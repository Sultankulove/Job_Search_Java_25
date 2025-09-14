package kg.attractor.job_search_java_25.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeViewDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoryService categoryService;



    @GetMapping("/resumes")
    public String listResumes(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) Long categoryId,
                              Model model,
                              HttpServletRequest req) {

        Page<ResumeListItemDto> resumes = (categoryId == null)
                ? resumeService.getResumes(PageRequest.of(page, 15))
                : resumeService.getResumesByCategory(categoryId, PageRequest.of(page, 15));

        model.addAttribute("title", "Список резюме");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("filterAction", req.getRequestURI());
        model.addAttribute("list", resumes);
        model.addAttribute("currentPage", resumes.getNumber());
        model.addAttribute("totalPages", resumes.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));
        return "list";
    }



    @GetMapping("/profile/resumes")
    public String myResumes(Authentication auth,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) Long categoryId,
                            Model model) {
        Long userId = userService.findUserIdByEmail(auth.getName());

        Page<ResumeListItemDto> resumes = (categoryId == null)
                ? resumeService.getResumesByAuthor(userId, PageRequest.of(page, 15))
                : resumeService.getResumesByAuthorAndCategory(userId, categoryId, PageRequest.of(page, 15));

        model.addAttribute("title", "Мои резюме");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("list", resumes);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));
        model.addAttribute("currentPage", resumes.getNumber());
        model.addAttribute("totalPages", resumes.getTotalPages());
        return "list";
    }



    @GetMapping("/resume/new")
    public String showResumeCreateForm(Model model) {
        ResumeUpsertDto dto = new ResumeUpsertDto();
        dto.setWorkExperiences(List.of(new WorkExperienceInfoDto()));
        dto.setEducationInfos(List.of(new EducationInfoDto()));
        dto.setContactInfos(List.of(new ContactInfoDto()));

        model.addAttribute("dto", dto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("formAction", "/resume/new");
        model.addAttribute("dtoName", "dto");
        model.addAttribute("formType", "resume");
        return "form";
    }


    @PostMapping("/resume/new")
    public String createResume(@ModelAttribute("dto") @Valid ResumeUpsertDto dto,
                               BindingResult bindingResult,
                               Authentication auth,
                               Model model) {

        dto.setWorkExperiences( dto.getWorkExperiences()==null ? List.of() :
                dto.getWorkExperiences().stream()
                        .filter(we -> we.getCompanyName()!=null && !we.getCompanyName().isBlank())
                        .toList());
        dto.setEducationInfos( dto.getEducationInfos()==null ? List.of() :
                dto.getEducationInfos().stream()
                        .filter(edu -> edu.getInstitution()!=null && !edu.getInstitution().isBlank())
                        .toList());
        dto.setContactInfos( dto.getContactInfos()==null ? List.of() :
                dto.getContactInfos().stream()
                        .filter(c -> c.getContactValue()!=null && !c.getContactValue().isBlank())
                        .toList());

        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/resume/new");
            model.addAttribute("formType", "resume");
            model.addAttribute("dtoName", "dto");
            model.addAttribute("categories", categoryService.findAll());
            return "form";
        }

        Long applicantId = userService.findUserIdByEmail(auth.getName());
        resumeService.saveResume(applicantId, dto);
        return "redirect:/resumes";
    }


    @GetMapping("/resume/{id}/edit")
    public String editResume(@PathVariable Long id, Model model, Authentication auth) {
        ResumeViewDto view = resumeService.getResumeById(id);
        var dto = ResumeUpsertDto.builder()
                .name(view.getName())
                .categoryId(view.getCategoryId())
                .salary(view.getSalary())
                .active(view.isActive())
                .build();
        model.addAttribute("dto", dto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("formAction", "/resume/" + id + "/edit");
        model.addAttribute("dtoName", "dto");
        model.addAttribute("formType", "resume");
        return "form";
    }

    @PostMapping("/resume/{id}/edit")
    public String updateResume(@PathVariable Long id,
                               @ModelAttribute("dto") @Valid ResumeUpsertDto dto,
                               BindingResult bindingResult,
                               Authentication authentication,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("formAction", "/resume/" + id + "/edit");
            model.addAttribute("dtoName", "dto");
            model.addAttribute("formType", "resume");
            return "form";
        }
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        resumeService.editResumeOwned(dto, id, applicantId);
        return "redirect:/profile/resumes";
    }
}
