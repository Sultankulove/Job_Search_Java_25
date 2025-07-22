package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeListDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResumeService {
    ResponseEntity<List<ResumeDto>> findResumeCategory(String name);

    ResponseEntity<List<ResumeDto>> findAllResume();
    void createResume(ResumeDto resumeDto);

    void deleteResume(long id);

    void editResume(long id, ResumeDto resumeDto);

    void updateResume(long id, ResumeDto resumeDto);

    ResponseEntity<List<ResumeListDto>> listOfCreatedResume(long applicantId);

    ResponseEntity<List<ResumeDto>> findResumeByCategoryName(String name);

    ResponseEntity<List<ResumeDto>> findResumeByCategoryId(long id);
}
