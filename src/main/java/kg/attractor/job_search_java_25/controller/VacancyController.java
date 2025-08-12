package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.dto.VacancyEditDto;
import kg.attractor.job_search_java_25.dto.VacancySearchDto;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final UserService userService;

    @GetMapping("vacancies")
    private String listVacancies(@ModelAttribute VacancySearchDto criteria, Model model) {
        List<VacancyDto> vacancies = vacancyService.searchVacancies(criteria);
        model.addAttribute("vacancies", vacancies);
        return "vacancies";
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
