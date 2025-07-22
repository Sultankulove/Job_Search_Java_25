package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
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
    private final RespondedApplicantService respondedApplicantService;

    @PostMapping("vacancy")
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("vacancy/{id}")
    public ResponseEntity<VacancyDto> editVacancy(@PathVariable long id, @RequestBody VacancyDto vacancyDto) {
        vacancyService.editVacancyById(id, vacancyDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("vacancy/{id}")
    public ResponseEntity<VacancyDto> deleteVacancy(@PathVariable long id) {
        vacancyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("vacancy")
    public ResponseEntity<List<VacancyDto>> getVacancy() {
        return vacancyService.getActiveVacancies();
    }

    @GetMapping("vacancy/categories/{name}")
    public ResponseEntity<List<VacancyDto>> getVacancyByCategoryName(@PathVariable String name) {
        return vacancyService.getVacancyByCategoryName(name);
    }

    @GetMapping("vacancy/categories/{id}")
    public ResponseEntity<List<VacancyDto>> getVacancyByCategoryId(@PathVariable Long id) {
        return vacancyService.getVacancyByCategoryId(id);
    }

    @GetMapping("vacancy/salary")
    public ResponseEntity<List<VacancyDto>> getVacancySortBySalary() {
        return vacancyService.getVacancySortBySalary();
    }

    @GetMapping("applicant/responded")
    public ResponseEntity<List<ApplicantDto>> getApplicantResponded() {
        return vacancyService.getApplicantResponded();
    }

    @GetMapping("vacancy/respondedApplicant/{vacancyId}")
    public ResponseEntity<List<RespondedApplicantDto>> getVacancyRespondedApplicant(@PathVariable Long vacancyId) {

        return respondedApplicantService.getVacancyRespondedApplicant(vacancyId);
    }

}
