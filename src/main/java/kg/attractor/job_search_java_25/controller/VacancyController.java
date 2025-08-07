package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.VacancyShortDto;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<List<VacancyShortDto>> listOfCreatedVacancies() {
        // Видны только владельцу профиля
        // Вывод: List {Название вакансий и Дата обновления вакансий.}


        // Должен получить user(id) владельца профиля и передать
        var shortVacanciesList = vacancyService.getShortVacanciesList(Long employerId);

        return ResponseEntity.ok(shortVacanciesList);
    }

    @PatchMapping("{id}")
    public String updateVacanciesById(@PathVariable Long id) {

        // Обновить (Только его дату обновления)
        return "OK";
    }

    @PutMapping("{id}")
    public String editVacanciesById(@PathVariable Long id) {

        // Редактировать
        return "OK";
    }

    @PatchMapping("{id}/published")
    public String vacanciesIsActiveById(@PathVariable Long id) {

        // Опубликован или не опубликован вакансия
        // Продумать надо
        return "OK";
    }

    @PostMapping
    public String createVacancy() {

        // Создать вакансию:
        // name, category(categoryId),salary, description, expFrom(Опыт от), expTo (Опыт до), isActive
        // authorId через auth


        return "OK";
    }

    @GetMapping("{id}")
    public String getVacancyById(@PathVariable Long id) {
        // Получение одной вакансии по id
        return "OK";
    }


    @DeleteMapping("{id}")
    public String deleteVacancyById(@PathVariable Long id) {
        // Удаление вакансии
        return "OK";
    }


    @GetMapping("search")
    public String searchVacancies(@RequestParam Map<String, String> params) {
        // Поиск вакансий с фильтрами/сортировкой
        return "OK";
    }



}
