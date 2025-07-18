package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.service.ApplicantService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployerController {

    public final VacancyService vacancyService;
    public final ResumeService resumeService;
    public final ApplicantService applicantService;
    // Работодатель
    // Доступно:
    // Создание вакансии
    @PostMapping("vacancy")
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancy) {
        return HttpStatus.CREATED;
    }
    // Редактирование вакансии
    @PutMapping("vacancy/{id}")
    public ResponseEntity<VacancyDto> editVacancy(@PathVariable long id, @RequestBody VacancyDto vacancyDto) {
        vacancyService.editById(id, vacancyDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Удаление вакансии
    @DeleteMapping("vacancy/{id}")
    public ResponseEntity<?> deleteVacancy(@PathVariable long id) {
        vacancyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Поиск всех резюме
    @GetMapping("resume")
    public ResponseEntity<List<ResumeDto>> allResume() {
        return resumeService.findAllResume();
    }

    // Поиск резюме по категории
    @GetMapping("resume/categories/{name}")
    public ResponseEntity<List<ResumeDto>> findResumeCategory(@PathVariable String name) {
        return resumeService.findResumeCategory(name);
    }

    // Поиск откликнувшихся соискателей на вакансию
    @GetMapping("employer/respondedApplicants")
    public ResponseEntity<List<RespondedApplicantDto>> FindRespondedApplicants() {
        return applicantService.findRespondedApplicants();
    }

    // Поиск Соискателя
    @GetMapping("find/applicant/{name}")
    public ResponseEntity<ApplicantDto> findApplicant(@PathVariable String name) {
        return applicantService.findApplicantByName(name);
    }
}
