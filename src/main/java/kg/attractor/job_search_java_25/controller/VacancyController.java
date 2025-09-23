package kg.attractor.job_search_java_25.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyViewDto;
import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoryService categoryService;



    @GetMapping("/vacancies")
    public String listVacancies(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) Long categoryId,
                                @RequestParam(required=false) BigDecimal salaryFrom,
                                @RequestParam(required=false) BigDecimal salaryTo,
                                Model model,
                                HttpServletRequest req) {

        Page<VacancyListItemDto> vacancies = (categoryId == null)
                ? vacancyService.getVacancies(PageRequest.of(page, 15), salaryFrom, salaryTo)
                : vacancyService.getVacanciesByCategory(categoryId, PageRequest.of(page, 15), salaryFrom, salaryTo);

        model.addAttribute("title", "Список вакансий");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("filterAction", req.getRequestURI());
        model.addAttribute("list", vacancies);
        model.addAttribute("type", "vacancy");
        model.addAttribute("currentPage", vacancies.getNumber());
        model.addAttribute("salaryFrom", salaryFrom);
        model.addAttribute("salaryTo", salaryTo);
        model.addAttribute("totalPages", vacancies.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));
        return "list";
    }

    @GetMapping("/profile/vacancies")
    public String myVacancies(Authentication auth,
                              Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(value = "categoryId", required = false) Long categoryId) {

        Long employerId = userService.findUserIdByEmail(auth.getName());

        Page<VacancyListItemDto> vacancies = (categoryId == null)
                ? vacancyService.findByEmployerId(employerId, PageRequest.of(page, 15))
                : vacancyService.findByEmployerIdAndCategory(employerId, categoryId, PageRequest.of(page, 15));

        model.addAttribute("title", "Мои вакансии");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("list", vacancies);
        model.addAttribute("type", "vacancy");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));
        model.addAttribute("currentPage", vacancies.getNumber());
        model.addAttribute("totalPages", vacancies.getTotalPages());
        return "list";
    }

    @GetMapping("vacancy/new")
    public String showCreateForm(Model model) {
        model.addAttribute("dto", new VacancyUpsertDto());
        model.addAttribute("categories", categoryService.findAll());
        return "CreatedForm";
    }

    @PostMapping("/vacancy/new")
    public String createVacancy(@ModelAttribute("dto") @Valid VacancyUpsertDto dto,
                                BindingResult bindingResult,
                                Authentication authentication,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "CreatedForm";
        }
        Long authorId = userService.findUserIdByEmail(authentication.getName());
        vacancyService.create(dto, authorId);
        return "redirect:/vacancies";
    }

    @GetMapping("/vacancy/{id}/edit")
    public String editVacancy(@PathVariable Long id, Model model) {
        VacancyViewDto view = vacancyService.getById(id);
        var dto = VacancyUpsertDto.builder()
                .name(view.getName())
                .description(view.getDescription())
                .categoryId(view.getCategoryId())
                .salary(view.getSalary())
                .expFrom(view.getExpFrom())
                .expTo(view.getExpTo())
                .active(view.getActive())
                .build();

        model.addAttribute("dto", dto);
        model.addAttribute("categories", categoryService.findAll());

        model.addAttribute("dtoName", "dto");
        model.addAttribute("formAction", "/vacancy/" + id + "/edit");
        model.addAttribute("formType", "vacancy");
        return "vacancy_form";
    }

    @PostMapping("/vacancy/{id}/edit")
    public String updateVacancy(@PathVariable Long id,
                                @ModelAttribute("dto") @Valid VacancyUpsertDto dto,
                                BindingResult br,
                                Authentication auth,
                                Model model) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("dtoName", "dto");
            model.addAttribute("formAction", "/vacancy/" + id + "/edit");
            model.addAttribute("formType", "vacancy");
            return "vacancy_form";
        }

        Long authorId = userService.findUserIdByEmail(auth.getName());
        vacancyService.editVacancyOwned(dto, id, authorId);
        return "redirect:/profile/vacancies";
    }
}