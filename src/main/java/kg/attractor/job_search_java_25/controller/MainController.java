package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(required = false) Long categoryId,
                        Model model,
                        Authentication auth) {

        if (auth == null || auth.getAuthorities() == null || auth.getAuthorities().isEmpty()) {
            Page<VacancyDto> vacancies = (categoryId == null)
                    ? vacancyService.getVacancies(PageRequest.of(page, 15))
                    : vacancyService.getVacanciesByCategory(categoryId, PageRequest.of(page, 15));

            model.addAttribute("title", "Список вакансий");
            model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
            model.addAttribute("list", vacancies);

            model.addAttribute("currentPage", vacancies.getNumber());
            model.addAttribute("totalPages", vacancies.getTotalPages());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));

            return "index";
        }

        String userRole = auth.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_APPLICANT".equals(userRole)) {
            Page<ResumeDto> resumes = (categoryId == null)
                    ? resumeService.getResumes(PageRequest.of(page, 15))
                    : resumeService.getResumesByCategory(categoryId, PageRequest.of(page, 15));

            model.addAttribute("title", "Список резюме");
            model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
            model.addAttribute("list", resumes);
            model.addAttribute("currentPage", resumes.getNumber());
            model.addAttribute("totalPages", resumes.getTotalPages());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));

        } else if ("ROLE_EMPLOYER".equals(userRole)) {
            Page<VacancyDto> vacancies = (categoryId == null)
                    ? vacancyService.getVacancies(PageRequest.of(page, 15))
                    : vacancyService.getVacanciesByCategory(categoryId, PageRequest.of(page, 15));

            model.addAttribute("title", "Список вакансий");
            model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
            model.addAttribute("list", vacancies);
            model.addAttribute("currentPage", vacancies.getNumber());
            model.addAttribute("totalPages", vacancies.getTotalPages());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));

        } else {
            System.err.println("Unknown role: " + userRole);
        }

        return "index";
    }


}
