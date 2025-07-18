package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    ResumeDao resumeDao;

    @Override
    public ResponseEntity<List<ResumeDto>> findResumeCategory(String name) {
        // TODO Должен возвращать список резюме по категории (категорию находим по имени)
        return null;
    }

    @Override
    public ResponseEntity<List<ResumeDto>> findAllResume() {
        List<Resume> resumes = resumeDao.findAll();
        List<ResumeDto> dtos = resumes.stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }


    @Override
    public void createResume(ResumeDto resumeDto) {
        Resume resume = toEntity(resumeDto);
        resumeDao.save(resume);
    }


    @Override
    public void deleteResume(long id) {
        resumeDao.deleteById((int) id);
    }


    @Override
    public void editResume(long id, ResumeDto resumeDto) {
        Resume resume = toEntity(resumeDto);
        resume.setId(id);
        resumeDao.update(resume);
    }

    public Resume toEntity(ResumeDto dto) {
        Resume resume = new Resume();
        resume.setId(dto.getId());
        resume.setName(dto.getName());
        resume.setCategoryId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setIsActive(dto.getIsActive());
        resume.setCreatedDate(dto.getCreatedDate());
        resume.setUpdatedDate(dto.getUpdatedDate());
        resume.setApplicantId(dto.getApplicantId());
        return resume;
    }

    public ResumeDto toDto(Resume resume) {
        ResumeDto dto = new ResumeDto();
        dto.setId(resume.getId());
        dto.setName(resume.getName());
        dto.setCategoryId(resume.getCategoryId());
        dto.setSalary(resume.getSalary());
        dto.setIsActive(resume.getIsActive());
        dto.setCreatedDate(resume.getCreatedDate());
        dto.setUpdatedDate(resume.getUpdatedDate());
        dto.setApplicantId(resume.getApplicantId());
        return dto;
    }


    }
