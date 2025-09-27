package kg.attractor.job_search_java_25.controller;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeUpsertDto;
import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.ContactTypeService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ContactTypeService contactTypeService;


    @GetMapping("/resumes")
    public String listResumes(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) Long categoryId,
                              Model model,
                              @RequestParam(required=false) BigDecimal salaryFrom,
                              @RequestParam(required=false) BigDecimal salaryTo,
                              HttpServletRequest req) {

        Page<ResumeListItemDto> resumes = (categoryId == null)
                ? resumeService.getResumes(PageRequest.of(page, 20), salaryFrom, salaryTo)
                : resumeService.getResumesByCategory(categoryId, PageRequest.of(page, 20), salaryFrom, salaryTo);

        model.addAttribute("title", "Resumes");
        model.addAttribute("headers", List.of("table.col.name", "table.col.category", "table.col.salary", "table.col.updated"));
        model.addAttribute("filterAction", req.getRequestURI());
        model.addAttribute("list", resumes);
        model.addAttribute("type", "resume");
        model.addAttribute("currentPage", resumes.getNumber());
        model.addAttribute("salaryFrom", salaryFrom);
        model.addAttribute("salaryTo", salaryTo);
        model.addAttribute("totalPages", resumes.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        Map<String, String> params = new LinkedHashMap<>();
        if (categoryId != null) {
            params.put("categoryId", categoryId.toString());
        }
        if (salaryFrom != null) {
            params.put("salaryFrom", salaryFrom.toPlainString());
        }
        if (salaryTo != null) {
            params.put("salaryTo", salaryTo.toPlainString());
        }
        model.addAttribute("params", params);
        return "list";
    }


    @GetMapping("/profile/resumes")
    public String myResumes(Authentication auth,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) Long categoryId,
                            @RequestParam(required=false) BigDecimal salaryFrom,
                            @RequestParam(required=false) BigDecimal salaryTo,
                            Model model) {
        Long userId = userService.findUserIdByEmail(auth.getName());

        Page<ResumeListItemDto> resumes = (categoryId == null)
                ? resumeService.getResumesByAuthor(userId, PageRequest.of(page, 20), salaryFrom, salaryTo)
                : resumeService.getResumesByAuthorAndCategory(userId, categoryId, PageRequest.of(page, 20), salaryFrom, salaryTo);

        model.addAttribute("title", "My resumes");
        model.addAttribute("headers", List.of("table.col.name", "table.col.category", "table.col.salary", "table.col.updated"));
        model.addAttribute("list", resumes);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("type", "resume");
        model.addAttribute("salaryFrom", salaryFrom);
        model.addAttribute("salaryTo", salaryTo);
        Map<String, String> params = new LinkedHashMap<>();
        params.put("categoryId", categoryId == null ? "" : categoryId.toString());
        model.addAttribute("params", params);
        model.addAttribute("currentPage", resumes.getNumber());
        model.addAttribute("totalPages", resumes.getTotalPages());
        return "list";
    }

    @GetMapping("/resume/new")
    public String showResumeCreateForm(Model model) {
        model.addAttribute("dto", new ResumeUpsertDto());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("formAction", "/resume/new");

        return "resume_form";
    }

    @PostMapping("/resume/new")
    public String createResume(@ModelAttribute("dto") @Valid ResumeUpsertDto dto,
                               BindingResult br,
                               Authentication auth,
                               Model model,
                               RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("contactTypes", contactTypeService.findAll());
            model.addAttribute("br", br);
            model.addAttribute("errors", br.getFieldErrors());
            model.addAttribute("formAction", "/resume/new");
            return "resume_form";
        }

        Long applicantId = userService.findUserIdByEmail(auth.getName());
        resumeService.saveResume(applicantId, dto);
        ra.addFlashAttribute("success", "resume.created");
        return "redirect:/profile/resumes";
    }


    @GetMapping("/resume/{id}/edit")
    public String editResume(@PathVariable Long id, Model model, Authentication auth) {
        ResumeUpsertDto dto = resumeService.getResumeByIdForEdit(id);
        model.addAttribute("dto", dto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("formAction", "/resume/" + id + "/edit");
        return "resume_form";
    }

    @PostMapping("/resume/{id}/edit")
    public String updateResume(@PathVariable Long id,
                               @ModelAttribute("dto") @Valid ResumeUpsertDto dto,
                               BindingResult br,
                               Authentication authentication,
                               Model model) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("contactTypes", contactTypeService.findAll());
            model.addAttribute("br", br);
            model.addAttribute("errors", br.getFieldErrors());
            model.addAttribute("formAction", "/resume/" + id + "/edit");
            return "resume_form";
        }
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        resumeService.editResumeOwned(dto, id, applicantId);
        return "redirect:/profile/resumes";
    }
}
