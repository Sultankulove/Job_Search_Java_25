package kg.attractor.job_search_java_25.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class VacancyController {
    @GetMapping("vacancies/list")
    public String listOfCreatedVacancies() {
        // Видны только владельцу профиля
        // Вывод: List {Название резюме и Дата обновления ваканции.}
        return "OK";
    }

    @PatchMapping("vacancies/{id}")
    public String updateVacanciesById(@PathVariable Long id) {

        // Обновить (Только его дату обновления)
        return "OK";
    }

    @PutMapping("vacancies/{id}")
    public String editVacanciesById(@PathVariable Long id) {

        // Редактировать
        return "OK";
    }

    @PatchMapping("vacancies/{id}/published")
    public String vacanciesIsActiveById(@PathVariable Long id) {

        // Опубликован или не опубликован вакансия
        // Продумать надо
        return "OK";
    }

    @PostMapping("vacancies")
    public String createVacancy() {

        // Создать вакансию:
        // name, category(categoryId),salary, description, expFrom(Опыт от), expTo (Опыт до), isActive
        // authorId через auth


        return "OK";
    }
}
