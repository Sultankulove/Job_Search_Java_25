package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.CategoryService;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoryService categoryService;



@GetMapping("/vacancies")
public String listVacancies(
        @RequestParam(value = "categoryId", required = false) String categoryId,
        Model model) {

    Long catId = null;
    if (categoryId != null && !categoryId.isBlank()) {
        try { catId = Long.parseLong(categoryId); } catch (NumberFormatException ignored) {}
    }

    var vacancies = (catId == null)
            ? vacancyService.findAllVacancies()
            : vacancyService.findByCategory(catId);

    model.addAttribute("title", "Список вакансий");
    model.addAttribute("headers", List.of("Название", "Категория", "Зарплата", "Обновлено"));
    model.addAttribute("list", vacancies);

    var cats = categoryService.findAll();
    model.addAttribute("categories", cats);

    model.addAttribute("params", Map.of("categoryId", categoryId == null ? "" : categoryId));

    return "list";
}





    @GetMapping("/profile/vacancies")
    public String myVacancies(Authentication auth, Model model) {
        Long employerId = userService.findUserIdByEmail(auth.getName());
        List<VacancyDto> vacancies = vacancyService.findByEmployer(employerId);

        model.addAttribute("title", "Мои вакансии");
        model.addAttribute("headers", List.of("Название", "Категория", "Статус", "Обновлено"));
        model.addAttribute("list", vacancies);
        return "list";
    }


    @GetMapping("vacancy/new")
    public String showCreateForm(Model model) {
        model.addAttribute("vacancy", new VacancyEditDto());
        return "vacancy_form";
    }

    @PostMapping("/vacancy/new")
    public String createVacancy(@ModelAttribute("vacancy") @Valid VacancyEditDto vacancyDto, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "vacancy_form";
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
