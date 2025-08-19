package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoryService categoryService;



    @GetMapping("/resumes")
    public String listResumes(@RequestParam(required=false) Long categoryId,
                              Model model) {
        List<ResumeDto> resumes = (categoryId == null)
                ? resumeService.findAll()
                : resumeService.findByCategory(categoryId);

        model.addAttribute("title", "Список резюме");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("list", resumes);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("params", Map.of("categoryId", String.valueOf(categoryId==null?"":categoryId)));
        return "list";
    }





    @GetMapping("/resume/new")
    public String showCreateForm(Model model) {
        model.addAttribute("resume", new ResumeEditDto());
        model.addAttribute("categories", categoryService.findAll());
        return "resume_form";
    }

    



    @PostMapping("/resume/new")
    public String createResume(@ModelAttribute("resume") @Valid ResumeEditDto resumeDto, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "resume_form";
        }
        Long applicantId = userService.findUserIdByEmail(authentication.getName());
        resumeService.createResume(applicantId, resumeDto);

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
