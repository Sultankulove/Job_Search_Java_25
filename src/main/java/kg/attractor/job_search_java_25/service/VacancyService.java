package kg.attractor.job_search_java_25.service;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VacancyService {

    List<VacancyShortDto> getShortVacanciesList(Long employerId);

    ResponseEntity<?> updateTime(Long id);

    void editVacancy(VacancyEditDto editVacancyEditDto, Long id, Long userId);

    void vacancyIsActiveById(Long vacancyId, VacancyIsActiveDto vacancyIsActiveDto);

    VacancyDto createVacancies(Long authorId, VacancyEditDto createVacancyEditDto);

    ResponseEntity<?> getVacancyById(Long id);

    void deleteVacancyById(Long id);

    void respondToVacancy(ResponseDto dto, Long userId);

    ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(Long vacancyId);

    List<VacancyShortDto> getPublicShortVacancies();

    void editVacancyOwned(@Valid VacancyEditDto editVacancyEditDto, Long vacancyId, Long authorId);

    void vacancyIsActiveOwned(Long vacancyId, @Valid VacancyIsActiveDto vacancyIsActiveDto, Long authorId);

    List<VacancyDto> searchVacancies(VacancySearchDto criteria);
}
