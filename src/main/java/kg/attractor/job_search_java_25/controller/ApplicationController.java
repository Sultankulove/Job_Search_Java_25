package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.RespondRequestDto;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.service.ApplicantService;
import kg.attractor.job_search_java_25.service.RespondedApplicantService;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final VacancyService vacancyService;
    private final RespondedApplicantService respondedApplicantService;

    // Отклик на вакансию
    @PostMapping("/vacancies/{vacancyId}/respond")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<?> respond(
            @PathVariable Long vacancyId,
            @RequestBody RespondRequestDto request,
            Principal principal
    ) {
        respondedApplicantService.respondToVacancy(principal.getName(), request.getResumeId(), vacancyId);
        return ResponseEntity.ok("Отклик успешно отправлен");
    }




    // мои отклики
    @GetMapping("/profile/responses")
    public ResponseEntity<List<ApplicantDto>> getApplicantResponded() {
        return vacancyService.getApplicantResponded();
    }

    // отклики на вакансию (для работодателя)
    @GetMapping("vacancies/respondedApplicant/{vacancyId}")
    public ResponseEntity<List<RespondedApplicantDto>> getVacancyRespondedApplicant(@PathVariable Long vacancyId) {

        return respondedApplicantService.getVacancyRespondedApplicant(vacancyId);
    }
}
