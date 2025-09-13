package kg.attractor.job_search_java_25.service;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.ResponseDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VacancyService {

    Page<VacancyListItemDto> getShortVacanciesList(Long employerId, Pageable p);

    ResponseEntity<?> updateTime(Long id);

    void edtVacancy(VacancyUpsertDto v, Long vacancyId, Long userId);

    void vacancyIsActive(Long vacancyId, ActiveDto activeDto);

    VacancyDto createVacancies(Long authorId, VacancyEditDto createVacancyEditDto);

    ResponseEntity<?> getVacancyById(Long id);

    void deleteVacancyById(Long id);

    void respondToVacancy(ResponseDto dto, Long userId);

    ResponseEntity<List<RespondedApplicantDto>> getResponsesByVacancy(Long vacancyId);

    List<VacancyShortDto> getPublicShortVacancies();

    void editVacancyOwned(@Valid VacancyEditDto editVacancyEditDto, Long vacancyId, Long authorId);

    void vacancyIsActiveOwned(Long vacancyId, @Valid VacancyIsActiveDto vacancyIsActiveDto, Long authorId);

    List<VacancyDto> searchVacancies(VacancySearchDto criteria);

    List<VacancyDto> findVacanciesById(Long userId);

    List<VacancyDto> findByEmployer(Long employerId);

    List<VacancyDto> findAllVacancies();

    Page<VacancyDto> getVacancies(Pageable pageable);

    Page<VacancyDto> getVacanciesByCategory(Long categoryId, PageRequest of);

    Page<VacancyDto> findByEmployerId(Long employerId, Pageable pageable);

    Page<VacancyDto> findByEmployerIdAndCategory(Long employerId, Long categoryId, Pageable pageable);
}
