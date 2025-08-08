package kg.attractor.job_search_java_25.controller;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.dto.VacancyEditDto;
import kg.attractor.job_search_java_25.dto.VacancyIsActiveDto;
import kg.attractor.job_search_java_25.dto.VacancyShortDto;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;
    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<VacancyShortDto>> list() {
        var list = vacancyService.getPublicShortVacancies();
        return ResponseEntity.ok(list);
    }

    @PatchMapping("{vacancyId}")
    public ResponseEntity<Void> updateVacanciesById(@PathVariable Long vacancyId) {

        log.info("PATCH /api/vacancies/{} — обновление времени", vacancyId);
        vacancyService.updateTime(vacancyId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{vacancyId}")
    public ResponseEntity<VacancyEditDto> editVacanciesById(@PathVariable Long vacancyId, @RequestBody @Valid VacancyEditDto editVacancyEditDto, Authentication authentication) {
        Long authorId = userService.findUserIdByEmail(authentication.getName());

        log.info("PUT /api/vacancies/{} — редактирование вакансии, authorId={}", vacancyId, authorId);

        vacancyService.editVacancy(editVacancyEditDto, vacancyId, authorId);
        vacancyService.editVacancyOwned(editVacancyEditDto, vacancyId, authorId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{vacancyId}/status")
    public ResponseEntity<VacancyIsActiveDto> vacanciesIsActiveById(@PathVariable Long vacancyId, @RequestBody @Valid VacancyIsActiveDto vacancyIsActiveDto, Authentication authentication) {
        Long authorId = userService.findUserIdByEmail(authentication.getName());
        // проверять владение вакансией authorId
        // Продумать надо...
        log.info("PATCH /api/vacancies/{}/status — публикация={}", vacancyId, vacancyIsActiveDto.getIsActive());
        vacancyService.vacancyIsActiveById(vacancyId, vacancyIsActiveDto);
        vacancyService.vacancyIsActiveOwned(vacancyId, vacancyIsActiveDto, authorId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<VacancyDto> createVacancy(@RequestBody @Valid VacancyEditDto createVacancyEditDto, Authentication authentication) {
        Long authorId = userService.findUserIdByEmail(authentication.getName());
        log.info("POST /api/vacancies — создание вакансии, authorId={}", authorId);
        VacancyDto saved = vacancyService.createVacancies(authorId, createVacancyEditDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



    @GetMapping("{id}")
    public ResponseEntity<VacancyDto> getVacancyById(@PathVariable Long id) {

        log.debug("GET /api/vacancies/{} — получить вакансию", id);

        return vacancyService.getVacancyById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVacancyById(@PathVariable Long id) {

        log.warn("DELETE /api/vacancies/{} — удаление вакансии", id);

        vacancyService.deleteVacancyById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("search")
    public String searchVacancies(@RequestParam Map<String, String> params) {

        // Поиск вакансий с фильтрами/сортировкой

        log.debug("GET /api/vacancies/search — фильтры={}", params);
        return "OK";
    }

}