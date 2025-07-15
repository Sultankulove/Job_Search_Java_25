package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VacancyService {
    void editById(long id, VacancyDto vacancyDto);

    void deleteById(long id);

    ResponseEntity<List<VacancyDto>> getActiveVacancies();

    ResponseEntity<List<VacancyDto>> getVacancyCategory(String name);

    ResponseEntity<List<ApplicantDto>> getApplicantResponded();
}
