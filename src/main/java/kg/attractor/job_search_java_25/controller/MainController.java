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
import java.util.LinkedHashMap;
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
                        @RequestParam(required = false) BigDecimal salaryFrom,
                        @RequestParam(required = false) BigDecimal salaryTo,
                        @RequestParam(required = false) String term,
                        @RequestParam(required = false) String sort,
                        Model model, Authentication auth, HttpServletRequest req) {

        String normalizedSort = (sort == null || sort.isBlank()) ? "-updateTime" : sort;
        String normalizedTerm = (term == null || term.isBlank()) ? null : term.trim();

        if (auth == null || auth.getAuthorities() == null || auth.getAuthorities().isEmpty()) {
            Page<VacancyListItemDto> vacancies = vacancyService.findPublicVacancies(
                    categoryId,
                    salaryFrom,
                    salaryTo,
                    normalizedTerm,
                    PageRequest.of(page, 15, vacancyService.resolveSort(normalizedSort))
            );

            fillListModel(req, model, "Список вакансий", vacancies,
                    categoryId, "vacancy", salaryFrom, salaryTo,
                    normalizedSort, normalizedTerm);
            return "index";
        }

        String role = auth.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_EMPLOYER".equals(role)) {
            Page<ResumeListItemDto> resumes = (categoryId == null)
                    ? resumeService.getResumes(PageRequest.of(page, 15), salaryFrom, salaryTo)
                    : resumeService.getResumesByCategory(categoryId, PageRequest.of(page, 15), salaryFrom, salaryTo);

            fillListModel(req, model, "Список резюме",
                    resumes, categoryId, "resume",
                    salaryFrom, salaryTo, null, null);

        } else if ("ROLE_APPLICANT".equals(role)) {
            Page<VacancyListItemDto> vacancies = vacancyService.findPublicVacancies(
                    categoryId,
                    salaryFrom,
                    salaryTo,
                    normalizedTerm,
                    PageRequest.of(page, 15, vacancyService.resolveSort(normalizedSort))
            );

            fillListModel(req, model, "Список вакансий",
                    vacancies, categoryId, "vacancy",
                    salaryFrom, salaryTo, normalizedSort, normalizedTerm);

        } else {
            log.warn("Unknown role: {}", role);
        }

        return "index";
    }



    private void fillListModel(HttpServletRequest req,
                               Model model,
                               String title,
                               Page<?> page,
                               Long categoryId,
                               String type,
                               BigDecimal salaryFrom,
                               BigDecimal salaryTo,
                               String sort,
                               String term) {

        model.addAttribute("title", title);
        model.addAttribute("headers", List.of(
                "Название",
                "Категория",
                "Зарплата",
                "Дата обновления"
        ));

        model.addAttribute("list", page);
        model.addAttribute("salaryFrom", salaryFrom);
        model.addAttribute("salaryTo", salaryTo);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());

        Map<String, String> params = new LinkedHashMap<>();
        params.put("categoryId", categoryId == null ? "" : categoryId.toString());
        if (term != null) {
            params.put("term", term);
        }
        if (sort != null) {
            params.put("sort", sort);
        }

        model.addAttribute("params", params);
        model.addAttribute("currentSort", sort);
        model.addAttribute("searchTerm", term == null ? "" : term);
        model.addAttribute("filterAction", req.getRequestURI());
    }


}
