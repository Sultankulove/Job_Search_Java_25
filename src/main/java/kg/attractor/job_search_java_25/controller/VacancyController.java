package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
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
                            @RequestParam(value = "categoryId", required = false) Long categoryId,
                            Model model) {

    Page<VacancyDto> vacancies = (categoryId == null)
            ? vacancyService.getVacancies(PageRequest.of(page, 15))
            : vacancyService.getVacanciesByCategory(categoryId, PageRequest.of(page, 15));

    model.addAttribute("title", "Список вакансий");
    model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
    model.addAttribute("list", vacancies);

    model.addAttribute("currentPage", vacancies.getNumber());
    model.addAttribute("totalPages",vacancies.getTotalPages());
    model.addAttribute("categories", categoryService.findAll());

    model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId));

    return "list";
}






    @GetMapping("/profile/vacancies")
    public String myVacancies(Authentication auth,
                              Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(value = "categoryId", required = false) Long categoryId) {

        Long employerId = userService.findUserIdByEmail(auth.getName());

        Page<VacancyDto> vacancies = (categoryId == null)
                ? vacancyService.findByEmployerId(employerId, PageRequest.of(page, 15))
                : vacancyService.findByEmployerIdAndCategory(employerId, categoryId, PageRequest.of(page, 15));
        model.addAttribute("title", "Мои вакансии");
        model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
        model.addAttribute("list", vacancies);

        model.addAttribute("categories", categoryService.findAll());

        model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId.toString()));

        return "list";
    }



    @GetMapping("vacancy/new")
    public String showCreateForm(Model model) {
        model.addAttribute("dto", new VacancyEditDto());
        model.addAttribute("categories", categoryService.findAll());
        return "CreatedForm";
    }


    @PostMapping("/vacancy/new")
    public String createVacancy(@ModelAttribute("dto") @Valid VacancyEditDto vacancyDto,
                                BindingResult bindingResult,
                                Authentication authentication,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "CreatedForm";
        }
        Long authorId = null;
        if (authentication != null) {
            authorId = userService.findUserIdByEmail(authentication.getName());
        }
        vacancyService.createVacancies(authorId, vacancyDto);
        return "redirect:/vacancies";
    }

    @GetMapping("/vacancy/{id}/edit")
    public String editVacancy(@PathVariable Long id, Model model) {
        VacancyEditDto dto = new VacancyEditDto();
        dto.setId(id);
        model.addAttribute("vacancy", dto);
        return "vacancy_form";
    }

    @PostMapping("/vacancy/{id}/edit")
    public String updateVacancy(@PathVariable Long id,
                                @ModelAttribute("vacancy") @Valid VacancyEditDto vacancyDto,
                                BindingResult bindingResult,
                                Authentication authentication,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "vacancy_form";
        }
        Long authorId = null;
        if (authentication != null) {
            authorId = userService.findUserIdByEmail(authentication.getName());
        }
        vacancyService.editVacancy(vacancyDto, id, authorId);
        return "redirect:/vacancies";
    }
}
