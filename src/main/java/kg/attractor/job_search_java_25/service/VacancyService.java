package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.model.Vacancy;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VacancyService {
    void editVacancyById(long id, VacancyDto vacancyDto);

    void deleteById(long id);

    ResponseEntity<List<VacancyDto>> getActiveVacancies();

    ResponseEntity<List<VacancyDto>> getVacancyByCategoryName(String name);

    ResponseEntity<List<ApplicantDto>> getApplicantResponded();

    Vacancy createVacancy(VacancyDto vacancyDto);

    ResponseEntity<List<VacancyDto>> getVacancyByCategoryId(Long id);

    ResponseEntity<List<VacancyDto>> getVacancySortBySalary();

    List<VacancyDto> getAllVacancies();
}
