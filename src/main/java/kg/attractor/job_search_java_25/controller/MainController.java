package kg.attractor.job_search_java_25.controller;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.ResumeService;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final CategoryService categoryService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(required = false) Long categoryId,
                        @RequestParam(required=false) BigDecimal salaryFrom,
                        @RequestParam(required=false) BigDecimal salaryTo,
                        Model model, Authentication auth, HttpServletRequest req) {

        if (auth == null || auth.getAuthorities() == null || auth.getAuthorities().isEmpty()) {
            Page<VacancyListItemDto> vacancies = (categoryId == null)
                    ? vacancyService.getVacancies(PageRequest.of(page, 15), salaryFrom, salaryTo)
                    : vacancyService.getVacanciesByCategory(categoryId, PageRequest.of(page, 15), salaryFrom, salaryTo);

            fillListModel(req, model, "Список вакансий", vacancies, categoryId, "vacancy", salaryFrom,salaryTo);
            return "index";
        }

        String role = auth.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_EMPLOYER".equals(role)) {
            Page<ResumeListItemDto> resumes = (categoryId == null)
                    ? resumeService.getResumes(PageRequest.of(page, 15), salaryFrom, salaryTo)
                    : resumeService.getResumesByCategory(categoryId, PageRequest.of(page, 15), salaryFrom, salaryTo);

            fillListModel(req, model, "Список резюме", resumes, categoryId, "resume", salaryFrom, salaryTo);

        } else if ("ROLE_APPLICANT".equals(role)) {
            Page<VacancyListItemDto> vacancies = (categoryId == null)
                    ? vacancyService.getVacancies(PageRequest.of(page, 15), salaryFrom, salaryTo)
                    : vacancyService.getVacanciesByCategory(categoryId, PageRequest.of(page, 15), salaryFrom, salaryTo);

            fillListModel(req, model, "Список вакансий", vacancies, categoryId, "vacancy", salaryFrom, salaryTo);

        } else {
            log.warn("Unknown role: {}", role);
        }

        return "index";
    }

    private void fillListModel(HttpServletRequest req, Model model, String title, Page<?> page, Long categoryId, String type, BigDecimal salaryFrom, BigDecimal salaryTo) {

        model.addAttribute("title", title);
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));

        model.addAttribute("list", page);
        model.addAttribute("salaryFrom", salaryFrom);
        model.addAttribute("salaryTo", salaryTo);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));
        model.addAttribute("filterAction", req.getRequestURI());
    }
}
