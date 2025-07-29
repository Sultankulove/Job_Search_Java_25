package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeListDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResumeService {

    ResponseEntity<ResumeDto> getResumeById(Long id);
    List<ResumeDto> findResumeByCategoryId(Long categoryId);

    List<ResumeDto> findAllResume();
    void createResume(ResumeDto resumeDto);

    void deleteResume(Long id);

    void editResume(Long id, ResumeDto resumeDto);

    void updateResume(Long id, ResumeDto resumeDto);

    ResponseEntity<List<ResumeListDto>> listOfCreatedResume(long applicantId);

    List<ResumeDto> findResumeByCategoryName(String name);

}
