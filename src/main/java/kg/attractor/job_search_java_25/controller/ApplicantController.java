package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.EmployerDto;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.service.EmployerService;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicantController {
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final EmployerService employerService;
    // Соискатель
    // Доступно:
    // Создание резюме
    @PostMapping("resume")
    public ResponseEntity<ResumeDto> createResume(@RequestBody ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return ResponseEntity.ok().body(resumeDto);
    }

    // Редактирование резюме
    @PutMapping("resume/{id}")
    public ResponseEntity<ResumeDto> updateResume(@PathVariable long id, @RequestBody ResumeDto resumeDto) {
        resumeService.editResume(id, resumeDto);
        return ResponseEntity.ok().body(resumeDto);
    }

    // Удаление резюме
    @DeleteMapping("resume")
    public HttpStatus deleteResume(@PathVariable long id) {
        resumeService.deleteResume(id);
        return HttpStatus.OK;
    }

    // Поиск всех активных вакансий
    @GetMapping("vacancy")
    public ResponseEntity<List<VacancyDto>> getVacancy() {
        return vacancyService.getActiveVacancies();
    }

    // Поиск вакансий по категории
    @GetMapping("vacancy/categories/{name}")
    public ResponseEntity<List<VacancyDto>> getVacancyCategory(@PathVariable String name) {
        return vacancyService.getVacancyCategory(name);
    }

    // Отклик на вакансию
    @GetMapping("applicant/responded")
    public ResponseEntity<List<ApplicantDto>> getApplicantResponded() {
        return vacancyService.getApplicantResponded();
    }

    // Поиск работодателя
    @GetMapping("find/employer/{name}")
    public ResponseEntity<EmployerDto> findApplicant(@PathVariable String name) {
        return employerService.findEmployerByName(name);
    }
}
