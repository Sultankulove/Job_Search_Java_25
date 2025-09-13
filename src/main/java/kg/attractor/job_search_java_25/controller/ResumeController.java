package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
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
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoryService categoryService;



    @GetMapping("/resumes")
    public String listResumes(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(required=false) Long categoryId,
                              Model model) {

        Page<ResumeDto> resumes = (categoryId == null)
                ? resumeService.getResumes(PageRequest.of(page, 15))
                : resumeService.getResumesByCategory(categoryId, PageRequest.of(page, 15));

        log.info("resumes: {}", resumes);
        model.addAttribute("title", "Список резюме");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("list", resumes);
        model.addAttribute("currentPage",resumes.getNumber());
        model.addAttribute("totalPages",resumes.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("params", Map.of("categoryId", String.valueOf(categoryId==null?"":categoryId)));
        return "list";
    }



    @GetMapping("/profile/resumes")
    public String myResumes(Authentication auth,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) Long categoryId,
                            Model model) {
        Long userId = userService.findUserIdByEmail(auth.getName());

        Page<ResumeDto> resumes;

        if (categoryId == null) {
            resumes = resumeService.getResumesByAuthor(userId, PageRequest.of(page, 15));
        } else {
            resumes = resumeService.getResumesByAuthorAndCategory(userId, categoryId, PageRequest.of(page, 15));
        }

        var cats = categoryService.findAll();

        model.addAttribute("title", "Мои резюме");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("list", resumes);
        model.addAttribute("categories", cats);
        model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));
        model.addAttribute("currentPage", resumes.getNumber());
        model.addAttribute("totalPages", resumes.getTotalPages());

        return "list";
    }



    @GetMapping("/resume/new")
    public String showResumeCreateForm(Model model) {
        ResumeEditDto dto = new ResumeEditDto();

        dto.getWorkExperiences().add(new WorkExperienceInfoDto());
        dto.getEducationInfos().add(new EducationInfoDto());
        dto.getContactInfos().add(new ContactInfoDto());


        model.addAttribute("dto", dto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("formAction", "/resume/new");
        model.addAttribute("dtoName", "dto");
        model.addAttribute("formType", "resume");
        return "form";
    }


    @PostMapping("/resume/new")
    public String createResume(@ModelAttribute("dto") ResumeEditDto dto,
                               BindingResult bindingResult,
                               Authentication auth,
                               Model model) {

        dto.setWorkExperiences(dto.getWorkExperiences().stream()
                .filter(we -> we.getCompanyName() != null && !we.getCompanyName().isBlank())
                .collect(Collectors.toList()));

        dto.setEducationInfos(dto.getEducationInfos().stream()
                .filter(edu -> edu.getInstitution() != null && !edu.getInstitution().isBlank())
                .collect(Collectors.toList()));

        dto.setContactInfos(dto.getContactInfos().stream()
                .filter(c -> c.getContactValue() != null && !c.getContactValue().isBlank())
                .collect(Collectors.toList()));

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
    public String editResume(@PathVariable Long id, Model model) {
        ResumeEditDto dto = new ResumeEditDto();
        model.addAttribute("resume", dto);
        return "resume_form";
    }

    @PostMapping("/resume/{id}/edit")
    public String updateResume(@PathVariable Long id, @ModelAttribute("resume") @Valid ResumeEditDto resumeDto, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "resume_form";
        }
        Long applicantId = null;
        if (authentication != null) {
            applicantId = userService.findUserIdByEmail(authentication.getName());
        }
        resumeService.editResume(resumeDto, id, applicantId);
        return "redirect:/resumes";
    }
}
