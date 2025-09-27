package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeAllInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


public interface ResumeService {

    void deleteOwned(Long resumeId, Long ownerId);

    Page<ResumeListItemDto> getResumesByAuthorAndCategory(Long applicantId, Long categoryId, Pageable pageable);


    Page<ResumeListItemDto> getResumesByAuthor(Long applicantId, Pageable pageable);


    Page<ResumeListItemDto> getResumesByAuthor(Long applicantId, Pageable pageable, BigDecimal salaryFrom, BigDecimal salaryTo);
    List<ResumeListItemDto> getActiveResumesByAuthor(Long applicantId);

    Page<ResumeListItemDto> getResumesByAuthorAndCategory(Long applicantId, Long categoryId, Pageable pageable, BigDecimal salaryFrom, BigDecimal salaryTo);

    Page<ResumeListItemDto> getResumesByCategory(Long categoryId, Pageable pageable, BigDecimal salaryFrom, BigDecimal salaryTo);

    Page<ResumeListItemDto> getResumes(Pageable pageable, BigDecimal salaryFrom, BigDecimal salaryTo);

    Page<ResumeListItemDto> getResumes(Pageable pageable);

    Page<ResumeListItemDto> getResumesByCategory(Long categoryId, Pageable pageable);

    ResumeViewDto getResumeById(Long id);

    @Transactional
    ResumeViewDto saveResume(Long applicantId, ResumeUpsertDto dto);

    @Transactional
    void editResumeOwned(ResumeUpsertDto dto, Long resumeId, Long applicantId);

    @Transactional
    void resumeIsActiveOwned(Long resumeId, ActiveDto dto, Long applicantId);

    @Transactional
    void updateTimeOwned(Long resumeId, Long applicantId);

    @Transactional
    void deleteResumeById(Long id);

    void checkResumeOwnership(Long resumeId, Long userId);

    ResumeUpsertDto getResumeByIdForEdit(Long id);

    ResumeAllInfoDto getResumeAllById(Long id);
}
