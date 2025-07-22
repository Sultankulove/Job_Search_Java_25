package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeListDto;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    @Override
    public List<ResumeDto> findResumeByCategoryId(Long categoryId) {
        List<Resume> list = resumeDao.findByCategoryId(categoryId);
        return list.stream()
                .map(e -> ResumeDto.builder()
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate().toLocalDateTime())
                        .updatedTime(e.getUpdateDate().toLocalDateTime())
                        .build())
                .toList();
    }

    @Override
    public List<ResumeDto> findAllResume() {
        List<Resume> list = resumeDao.findAll();
        return list.stream()
                .map(e -> ResumeDto.builder()
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate().toLocalDateTime())
                        .updatedTime(e.getUpdateDate().toLocalDateTime())
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
                        .id(e.getId())
                        .applicantId(e.getApplicantId())
                        .name(e.getName())
                        .categoryId(e.getCategoryId())
                        .salary(e.getSalary())
                        .isActive(e.getIsActive())
                        .createdDate(e.getCreatedDate().toLocalDateTime())
                        .updatedTime(e.getUpdateDate().toLocalDateTime())
                        .build())
                .toList();
    }


}
