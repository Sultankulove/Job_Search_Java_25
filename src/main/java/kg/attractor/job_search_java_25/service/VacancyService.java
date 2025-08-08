package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VacancyService {

    List<VacancyShortDto> getShortVacanciesList(Long employerId);

    ResponseEntity<Void> updateTime(Long id);

    void editVacancy(VacancyEditDto editVacancyEditDto, Long id, Long userId);

    void vacancyIsActiveById(Long vacancyId, VacancyIsActiveDto vacancyIsActiveDto);

    VacancyEditDto createVacancies(Long authorId, VacancyEditDto createVacancyEditDto);

    ResponseEntity<VacancyDto> getVacancyById(Long id);

    void deleteVacancyById(Long id);

    void respondToVacancy(ResponseDto dto, Long userId);

    ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(Long vacancyId);
}
