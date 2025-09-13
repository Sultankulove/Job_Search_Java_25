package kg.attractor.job_search_java_25.service;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResumeService {
    List<ResumeShortDto> getShortResumesList(Long applicantId);

    ResponseEntity<?> updateTime(Long resumeId);

    void editResume(ResumeEditDto resumeEditDto, Long resumeId, Long applicantId);

    void resumeIsActiveById(Long resumeId, ResumeIsActiveDto resumeIsActiveDto);

    ResumeEditDto saveResume(Long applicantId, ResumeEditDto resumeEditDto);

    ResponseEntity<?> getResumeById(Long id);

    void deleteResumeById(Long id);

    void updateTimeOwned(Long id, Long applicantId);

    void editResumeOwned(@Valid ResumeEditDto resumeEditDto, Long resumeId, Long applicantId);

    void resumeIsActiveOwned(Long resumeId, @Valid ResumeIsActiveDto resumeIsActiveDto, Long applicantId);

    List<ResumeDto> searchResumes(ResumeSearchDto criteria);

    List<ResumeListViewDto> findAllForList(Long applicantId);

    List<ResumeDto> findResumesById(Long applicantId);

    List<ResumeDto> findAll();

    List<ResumeDto> findByCategory(Long categoryId);

    List<ResumeDto> findByAuthor(Long userId);

    Page<ResumeDto> getResumes(Pageable pageable);

    Page<ResumeDto> getResumesByCategory(Long categoryId, PageRequest of);

    Page<ResumeDto> getResumesByAuthor(Long userId, Pageable pageable);

    Page<ResumeDto> getResumesByAuthorAndCategory(Long userId, Long categoryId, Pageable pageable);
}
