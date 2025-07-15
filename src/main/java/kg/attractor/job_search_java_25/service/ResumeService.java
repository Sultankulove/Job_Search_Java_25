package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ResumeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResumeService {
    ResponseEntity<List<ResumeDto>> findResumeCategory(String name);

    ResponseEntity<ResumeDto> findAllResume();

    void createResume(ResumeDto resumeDto);

    void deleteResume(long id);

    void editResume(long id, ResumeDto resumeDto);
}
