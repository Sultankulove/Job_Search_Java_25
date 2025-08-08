package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeEditDto;
import kg.attractor.job_search_java_25.dto.ResumeIsActiveDto;
import kg.attractor.job_search_java_25.dto.ResumeShortDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResumeService {
    List<ResumeShortDto> getShortResumesList(Long applicantId);

    ResponseEntity<Void> updateTime(Long resumeId);

    void editResume(ResumeEditDto resumeEditDto, Long resumeId, Long applicantId);

    void resumeIsActiveById(Long resumeId, ResumeIsActiveDto resumeIsActiveDto);

    ResumeEditDto createResume(Long applicantId, ResumeEditDto resumeEditDto);

    ResponseEntity<ResumeDto> getResumeById(Long id);

    void deleteResumeById(Long id);
}
