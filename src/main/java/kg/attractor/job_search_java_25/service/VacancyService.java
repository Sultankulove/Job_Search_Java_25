package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancySearchDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

public interface VacancyService {

    Page<VacancyListItemDto> getVacancies(Pageable pageable);

    Page<VacancyListItemDto> getVacancies(Pageable pageable, BigDecimal salaryFrom, BigDecimal salaryTo);

    Page<VacancyListItemDto> getVacanciesByCategory(Long categoryId, Pageable pageable, BigDecimal salaryFrom, BigDecimal salaryTo);

    Page<VacancyListItemDto> getVacanciesByCategory(Long categoryId, Pageable pageable);

    Page<VacancyListItemDto> findByEmployerId(Long employerId, Pageable pageable);

    Page<VacancyListItemDto> findByEmployerIdAndCategory(Long employerId, Long categoryId, Pageable pageable);

    List<VacancyListItemDto> search(VacancySearchDto c);

    Page<VacancyListItemDto> findPublicVacancies(Long categoryId,
                                                 BigDecimal salaryFrom,
                                                 BigDecimal salaryTo,
                                                 String term,
                                                 Pageable pageable);

    Sort resolveSort(String sort);

    Page<VacancyListItemDto> findList(Long authorId, Pageable pageable);

    void touchOwned(Long vacancyId, Long authorId);

    void editVacancyOwned(VacancyUpsertDto dto, Long vacancyId, Long authorId);

    void setActiveOwned(Long vacancyId, ActiveDto activeDto, Long authorId);

    VacancyViewDto create(VacancyUpsertDto dto, Long authorId);

    VacancyViewDto getById(Long id);

    void deleteOwned(Long vacancyId, Long authorId);

    void respondToVacancy(ResponseDto dto, Long applicantId);
    List<RespondedApplicantDto> getResponsesByVacancy(Long vacancyId, Long employerId);

}
