package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.RespondedApplicantService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    // Список вакансий
    @GetMapping("vacancies")
    public ResponseEntity<List<VacancyDto>> getListVacancy() {
        vacancyService.getAllVacancies();
        return new ResponseEntity<>(vacancyService.getAllVacancies(), HttpStatus.OK);
    }

    // Конкретная вакансия
    @GetMapping("vacancies/{id}")
    public ResponseEntity<VacancyDto> getVacancy(@PathVariable Long id) {
        return vacancyService.getVacancyById(id);
    }

    // Создать вакансию
    @PostMapping("vacancies")
    public Vacancy createVacancy(@RequestBody VacancyDto vacancyDto) {
        return vacancyService.createVacancy(vacancyDto);
    }

    // Редактировать вакансию
    @PutMapping("vacancies/{id}")
    public ResponseEntity<VacancyDto> editVacancy(@PathVariable Long id, @RequestBody VacancyDto vacancyDto) {
        vacancyService.editVacancyById(id, vacancyDto);
        return new ResponseEntity<>(vacancyDto, HttpStatus.OK);
    }

    // Удалить вакансию
    @DeleteMapping("vacancies/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        vacancyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("vacancies/categories/{name}")
    public ResponseEntity<List<VacancyDto>> getVacancyByCategoryName(@PathVariable String name) {
        return vacancyService.getVacancyByCategoryName(name);
    }

    @GetMapping("vacancies/categories/{id}")
    public ResponseEntity<List<VacancyDto>> getVacancyByCategoryId(@PathVariable Long id) {
        return vacancyService.getVacancyByCategoryId(id);
    }

    @GetMapping("vacancies/salary")
    public ResponseEntity<List<VacancyDto>> getVacancySortBySalary() {
        return vacancyService.getVacancySortBySalary();
    }

}
