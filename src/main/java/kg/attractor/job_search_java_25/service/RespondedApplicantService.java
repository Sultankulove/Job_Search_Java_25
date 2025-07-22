package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RespondedApplicantService {
    ResponseEntity<List<RespondedApplicantDto>> getVacancyRespondedApplicant(Long vacancyId);
}
