package kg.attractor.job_search_java_25.service.impl;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeListDto;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public ResponseEntity<ResumeDto> getResumeById(Long id) {
        Optional<Resume> optionalResume = resumeDao.getResumeById(id);

        if (optionalResume.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Resume resume = optionalResume.get();
        ResumeDto dto = new ResumeDto();
        dto.setApplicantId(resume.getId());
        dto.setName(resume.getName());
        dto.setCategoryId(resume.getCategoryId());
        dto.setSalary(resume.getSalary());
        dto.setIsActive(resume.getIsActive());
        dto.setCreatedDate(resume.getCreatedDate().toLocalDateTime());
        dto.setUpdatedTime(resume.getUpdateTime().toLocalDateTime());

        return ResponseEntity.ok(dto);
    }

    @Override
    public List<ResumeDto> findResumeByCategoryId(Long categoryId) {
        List<Resume> list = resumeDao.findByCategoryId(categoryId);
        return list.stream()
                .map(e -> ResumeDto.builder()
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate().toLocalDateTime())
                        .updatedTime(e.getUpdateTime().toLocalDateTime())
                        .build())
                .toList();
    }

    @Override
    public List<ResumeDto> findAllResume() {
        List<Resume> list = resumeDao.findAll();
        return list.stream()
                .map(e -> ResumeDto.builder()
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate().toLocalDateTime())
                        .updatedTime(e.getUpdateTime().toLocalDateTime())
                        .build())
                        .toList();
    }


    @Override
    public void createResume(ResumeDto resumeDto) {
        resumeDao.createResume(resumeDto);
    }


    @Override
    public void deleteResume(Long id) {
        resumeDao.deleteById(id);
    }

    @Override
    public void editResume(Long id, ResumeDto resumeDto) {
        resumeDao.edit(id, resumeDto);
    }

    @Override
    public void updateResume(Long id, ResumeDto resumeDto) {

    }

    @Override
    public ResponseEntity<List<ResumeListDto>> listOfCreatedResume(long applicantId) {
        return null;
    }

    @Override
    public List<ResumeDto> findResumeByCategoryName(String name) {
        List<Resume> list = resumeDao.findByCategoryName(name);
        return list.stream()
                .map(e -> ResumeDto.builder()
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate().toLocalDateTime())
                        .updatedTime(e.getUpdateTime().toLocalDateTime())
                        .build())
                .toList();
    }


}
