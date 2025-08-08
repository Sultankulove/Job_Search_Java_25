package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.dto.VacancyEditDto;
import kg.attractor.job_search_java_25.dto.VacancyIsActiveDto;
import kg.attractor.job_search_java_25.dto.VacancyShortDto;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        Long employerId = 1L; // Хард-код
        // Должен получить user(id) владельца профиля и передать
        var shortVacanciesList = vacancyService.getShortVacanciesList(employerId);

        return ResponseEntity.ok(shortVacanciesList);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateVacanciesById(@PathVariable Long vacancyId) {
        // Обновить (Только его дату обновления)
        vacancyService.updateTime(vacancyId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{vacancyId}")
    public ResponseEntity<VacancyEditDto> editVacanciesById(@PathVariable Long vacancyId, @RequestBody VacancyEditDto editVacancyEditDto) {

        // Нужно взять id пользователя(auth) и передать как users(id)
        Long authorId = 1L; // хард-код

        vacancyService.editVacancy(editVacancyEditDto, vacancyId, authorId);
        // Редактировать
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{vacancyId}/status")
    public ResponseEntity<VacancyIsActiveDto> vacanciesIsActiveById(@PathVariable Long vacancyId, @RequestBody VacancyIsActiveDto vacancyIsActiveDto) {

        // Опубликован или не опубликован вакансия
        // Продумать надо
        vacancyService.vacancyIsActiveById(vacancyId, vacancyIsActiveDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<VacancyEditDto> createVacancy(@RequestBody VacancyEditDto createVacancyEditDto) {

        // Создать вакансию:
        // name, category(categoryId),salary, description, expFrom(Опыт от), expTo (Опыт до), isActive
        // authorId через auth

        Long authorId = 1L;
        VacancyEditDto saved = vacancyService.createVacancies(authorId, createVacancyEditDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



    @GetMapping("{id}")
    public ResponseEntity<VacancyDto> getVacancyById(@PathVariable Long id) {
        // Получение одной вакансии по id
        return vacancyService.getVacancyById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVacancyById(@PathVariable Long id) {

        // Удаление вакансии
        vacancyService.deleteVacancyById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("search")
    public String searchVacancies(@RequestParam Map<String, String> params) {
        // Поиск вакансий с фильтрами/сортировкой
        return "OK";
    }



}
