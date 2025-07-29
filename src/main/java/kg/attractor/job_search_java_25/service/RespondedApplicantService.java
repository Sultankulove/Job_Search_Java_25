package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RespondedApplicantService {
    void respondToVacancy(String email, Long resumeId, Long vacancyId);
    ResponseEntity<List<RespondedApplicantDto>> getVacancyRespondedApplicant(Long vacancyId);
}
