package kg.attractor.job_search_java_25.service;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResumeService {
    List<ResumeShortDto> getShortResumesList(Long applicantId);

    ResponseEntity<?> updateTime(Long resumeId);

    void editResume(ResumeEditDto resumeEditDto, Long resumeId, Long applicantId);

    void resumeIsActiveById(Long resumeId, ResumeIsActiveDto resumeIsActiveDto);

    ResumeEditDto createResume(Long applicantId, ResumeEditDto resumeEditDto);

    ResponseEntity<?> getResumeById(Long id);

    void deleteResumeById(Long id);

    void updateTimeOwned(Long id, Long applicantId);

    void editResumeOwned(@Valid ResumeEditDto resumeEditDto, Long resumeId, Long applicantId);

    void resumeIsActiveOwned(Long resumeId, @Valid ResumeIsActiveDto resumeIsActiveDto, Long applicantId);

    List<ResumeDto> searchResumes(ResumeSearchDto criteria);

    List<ResumeDto> findAllForList(Long applicantId);
}
