package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicantService {
    ResponseEntity<List<RespondedApplicantDto>> findRespondedApplicants();

    ResponseEntity<ApplicantDto> findApplicantByName(String name);
}
