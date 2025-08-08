package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public List<ResumeShortDto> getShortResumesList(Long applicantId) {

        List<ResumeShortDto> shortResumes;
        shortResumes = resumeDao.getAllResumesById(applicantId)
                .stream()
                .map(r -> new ResumeShortDto(r.getName(), r.getUpdateTime()))
                .toList();
        return shortResumes;
    }

    @Override
    public ResponseEntity<Void> updateTime(Long resumeId) {
        resumeDao.updateTime(resumeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void editResume(ResumeEditDto resumeEditDto, Long resumeId, Long applicantId) {

        Resume resume = new Resume();
        resume.setName(resumeEditDto.getName());
        resume.setCategoryId(resumeEditDto.getCategoryId());
        resume.setSalary(resumeEditDto.getSalary());
        resume.setIsActive(resumeEditDto.getIsActive());

        resumeDao.editResume(resume, resumeId, applicantId);
    }

    @Override
    public void resumeIsActiveById(Long resumeId, ResumeIsActiveDto resumeIsActiveDto) {
        boolean isActive = resumeIsActiveDto.getIsActive();

        resumeDao.resumeIsActive(resumeId, isActive);
    }

    @Override
    public ResumeEditDto createResume(Long applicantId, ResumeEditDto resumeEditDto) {
        Resume resume = new Resume();
        resume.setName(resumeEditDto.getName());
        resume.setCategoryId(resumeEditDto.getCategoryId());
        resume.setSalary(resumeEditDto.getSalary());
        resume.setIsActive(resumeEditDto.getIsActive());
        resume.setApplicantId(applicantId);

        resumeDao.createResume(resume);
        return ResumeEditDto.builder()
                        .name(resume.getName())
                                .categoryId(resume.getCategoryId())
                                        .salary(resume.getSalary())
                                                .isActive(resume.getIsActive())
                                                        .build();
    }

    @Override
    public ResponseEntity<ResumeDto> getResumeById(Long id) {
        Optional<Resume> resume = resumeDao.getResumeById(id);
        if (resume.isPresent()) {
            ResumeDto dto = new ResumeDto();
            dto.setId(resume.get().getId());
            dto.setApplicantId(resume.get().getApplicantId());
            dto.setName(resume.get().getName());
            dto.setCategoryId(resume.get().getCategoryId());
            dto.setSalary(resume.get().getSalary());
            dto.setIsActive(resume.get().getIsActive());
            dto.setCreatedDate(resume.get().getCreatedDate());
            dto.setUpdateTime(resume.get().getUpdateTime());

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void deleteResumeById(Long id) {
        resumeDao.deleteResumeById(id);

    }
}
