package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.publication.PublicationDetailDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationListItemDto;
import kg.attractor.job_search_java_25.dto.publication.PublicationUpsertDto;
import kg.attractor.job_search_java_25.dto.publication.comment.PublicationCommentDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.PublicationService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.util.FileUtil;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) Long categoryId,
                       @RequestParam(required = false) String term,
                       @RequestParam(required = false) String sort,
                       Model model) {

        String normalizedSort = (sort == null || sort.isBlank()) ? "-updatedAt" : sort;
        String normalizedTermRaw = term == null ? null : term.trim();
        Page<PublicationListItemDto> publications = publicationService.findPublications(
                PageRequest.of(page, 12),
                categoryId,
                normalizedSort,
                normalizedTermRaw,
                false
        );

        String normalizedTerm = normalizedTermRaw == null ? "" : normalizedTermRaw;

        Map<String, String> params = new LinkedHashMap<>();
        params.put("categoryId", categoryId == null ? "" : categoryId.toString());
        params.put("term", normalizedTerm);
        params.put("sort", normalizedSort);

        model.addAttribute("list", publications);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("filterAction", "/publications");
        model.addAttribute("selectedCategoryId", categoryId == null ? "" : categoryId.toString());
        model.addAttribute("params", params);
        model.addAttribute("currentSort", normalizedSort);
        model.addAttribute("searchTerm", normalizedTerm);
        return "publication/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("dto", new PublicationUpsertDto());
        model.addAttribute("categories", categoryService.findAll());
        return "publication/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("dto") PublicationUpsertDto dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "publication/form";
        }
        Long authorId = userService.getRequiredUserId();
        PublicationDetailDto detail = publicationService.createPublication(dto, authorId);
        redirectAttributes.addFlashAttribute("success", "publication.created");
        return "redirect:/publications/" + detail.getId();
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Long viewerId = userService.getUserIdOrNull();
        boolean isModerator = userService.hasRole("ROLE_ADMIN");
        PublicationDetailDto detail = publicationService.getPublication(id, viewerId, isModerator);
        model.addAttribute("detail", detail);
        model.addAttribute("isModerator", isModerator);
        model.addAttribute("isAuthor", viewerId != null && viewerId.equals(detail.getAuthorId()));
        model.addAttribute("commentDto", PublicationCommentDto.builder().build());
        return "publication/view";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Long userId = userService.getRequiredUserId();
        PublicationDetailDto detail = publicationService.getPublication(id, userId, false);
        if (!userId.equals(detail.getAuthorId())) {
            throw new ForbiddenException("Not your publication");
        }
        PublicationUpsertDto dto = PublicationUpsertDto.builder()
                .categoryId(detail.getCategoryId())
                .title(detail.getTitle())
                .content(detail.getContent())
                .build();
        model.addAttribute("dto", dto);
        model.addAttribute("detail", detail);
        model.addAttribute("categories", categoryService.findAll());
        return "publication/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("dto") PublicationUpsertDto dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Long userId = userService.getRequiredUserId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("detail", publicationService.getPublication(id, userId, false));
            return "publication/form";
        }
        PublicationDetailDto detail = publicationService.updatePublication(id, dto, userId);
        redirectAttributes.addFlashAttribute("success", "publication.updated");
        return "redirect:/publications/" + detail.getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Long userId = userService.getRequiredUserId();
        boolean isModerator = userService.hasRole("ROLE_ADMIN");
        publicationService.softDeletePublication(id, userId, isModerator);
        redirectAttributes.addFlashAttribute("success", "publication.deleted");
        return "redirect:/publications";
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id,
                             @RequestParam("content") String content,
                             RedirectAttributes redirectAttributes) {
        Long userId = userService.getRequiredUserId();
        if (content == null || content.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "comment.validation.empty");
            return "redirect:/publications/" + id + "#comments";
        }
        publicationService.addComment(id, userId, content.trim());
        return "redirect:/publications/" + id + "#comments";
    }

    @PostMapping("/{publicationId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long publicationId,
                                @PathVariable Long commentId,
                                RedirectAttributes redirectAttributes) {
        Long userId = userService.getRequiredUserId();
        boolean isModerator = userService.hasRole("ROLE_ADMIN");
        publicationService.deleteComment(commentId, userId, isModerator);
        redirectAttributes.addFlashAttribute("success", "comment.deleted");
        return "redirect:/publications/" + publicationId + "#comments";
    }

    @GetMapping("/{id}/cover")
    @ResponseBody
    public ResponseEntity<?> cover(@PathVariable Long id) {
        PublicationDetailDto detail = publicationService.getPublication(id, null, true);
        if (detail.getCover() == null) {
            throw new NotFoundException("Cover not found");
        }
        return FileUtil.downloadImage(detail.getCover(), "publications");
    }
}
