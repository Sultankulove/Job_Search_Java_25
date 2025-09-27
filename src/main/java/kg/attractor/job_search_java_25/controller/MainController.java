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
import org.springframework.data.domain.Sort;
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

    private static final int PAGE_SIZE = 20;

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
                        Model model,
                        Authentication authentication,
                        HttpServletRequest request) {

        String normalizedSort = (sort == null || sort.isBlank()) ? "-updateTime" : sort.trim();
        String normalizedTerm = (term == null || term.isBlank()) ? null : term.trim();
        Sort sortOrder = vacancyService.resolveSort(normalizedSort);

        if (authentication == null || authentication.getAuthorities() == null || authentication.getAuthorities().isEmpty()) {
            Page<VacancyListItemDto> vacancies = vacancyService.findPublicVacancies(
                    categoryId,
                    salaryFrom,
                    salaryTo,
                    normalizedTerm,
                    PageRequest.of(page, PAGE_SIZE, sortOrder)
            );
            fillListModel(request, model, "Vacancies", vacancies, categoryId, "vacancy",
                    salaryFrom, salaryTo, normalizedSort, normalizedTerm);
            return "index";
        }

        boolean isEmployer = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_EMPLOYER".equals(a.getAuthority()));
        boolean isApplicant = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_APPLICANT".equals(a.getAuthority()));

        if (isEmployer) {
            Page<ResumeListItemDto> resumes = (categoryId == null)
                    ? resumeService.getResumes(PageRequest.of(page, PAGE_SIZE), salaryFrom, salaryTo)
                    : resumeService.getResumesByCategory(categoryId, PageRequest.of(page, PAGE_SIZE), salaryFrom, salaryTo);
            fillListModel(request, model, "Resumes", resumes, categoryId, "resume",
                    salaryFrom, salaryTo, null, null);
        } else if (isApplicant) {
            Page<VacancyListItemDto> vacancies = vacancyService.findPublicVacancies(
                    categoryId,
                    salaryFrom,
                    salaryTo,
                    normalizedTerm,
                    PageRequest.of(page, PAGE_SIZE, sortOrder)
            );
            fillListModel(request, model, "Vacancies", vacancies, categoryId, "vacancy",
                    salaryFrom, salaryTo, normalizedSort, normalizedTerm);
        } else {
            log.warn("Unknown role detected while rendering index page");
            Page<VacancyListItemDto> vacancies = vacancyService.findPublicVacancies(
                    categoryId,
                    salaryFrom,
                    salaryTo,
                    normalizedTerm,
                    PageRequest.of(page, PAGE_SIZE, sortOrder)
            );
            fillListModel(request, model, "Vacancies", vacancies, categoryId, "vacancy",
                    salaryFrom, salaryTo, normalizedSort, normalizedTerm);
        }

        return "index";
    }

    private void fillListModel(HttpServletRequest request,
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
                "table.col.name",
                "table.col.category",
                "table.col.salary",
                "table.col.updated"));

        model.addAttribute("list", page);
        model.addAttribute("salaryFrom", salaryFrom);
        model.addAttribute("salaryTo", salaryTo);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryId", categoryId == null ? "" : categoryId.toString());

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
        if (term != null && !term.isBlank()) {
            params.put("term", term);
        }
        if (sort != null && !sort.isBlank()) {
            params.put("sort", sort);
        }

        model.addAttribute("params", params);
        model.addAttribute("currentSort", sort);
        model.addAttribute("searchTerm", term == null ? "" : term);
        model.addAttribute("filterAction", request.getRequestURI());
    }
}
