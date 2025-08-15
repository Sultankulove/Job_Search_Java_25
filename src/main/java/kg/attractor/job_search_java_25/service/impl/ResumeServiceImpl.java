package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public List<ResumeShortDto> getShortResumesList(Long applicantId) {
        log.debug("ResumeService.getShortResumesList(applicantId={})", applicantId);
        List<ResumeShortDto> shortResumes;
        shortResumes = resumeDao.getAllResumesById(applicantId)
                .stream()
                .map(r -> new ResumeShortDto(r.getName(), r.getUpdateTime()))
                .toList();
        log.info("Резюме: короткий список size={}", shortResumes.size());
        return shortResumes;
    }

    @Override
    public ResponseEntity<?> updateTime(Long resumeId) {
        log.info("Резюме: обновление времени id={}", resumeId);
        resumeDao.updateTime(resumeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void editResume(ResumeEditDto resumeEditDto, Long resumeId, Long applicantId) {
        log.info("Резюме: редактирование id={}, applicantId={}", resumeId, applicantId);
        Resume resume = new Resume();
        resume.setName(resumeEditDto.getName());
        resume.getCategory().setId(resumeEditDto.getCategoryId());
        resume.setSalary(resumeEditDto.getSalary());
        resume.setIsActive(resumeEditDto.getIsActive());
        resumeDao.editResume(resume, resumeId, applicantId);
        log.debug("Резюме: отредактировано id={}", resumeId);
    }

    @Override
    public void resumeIsActiveById(Long resumeId, ResumeIsActiveDto resumeIsActiveDto) {
        boolean isActive = resumeIsActiveDto.getIsActive();
        log.info("Резюме: публикация id={}, isActive={}", resumeId, isActive);
        resumeDao.resumeIsActive(resumeId, isActive);
    }

    @Override
    public ResumeEditDto createResume(Long applicantId, ResumeEditDto resumeEditDto) {
        log.info("Резюме: создание applicantId={}", applicantId);
        Resume resume = new Resume();
        resume.setName(resumeEditDto.getName());
        resume.getCategory().setId(resumeEditDto.getCategoryId());
        resume.setSalary(resumeEditDto.getSalary());
        resume.setIsActive(resumeEditDto.getIsActive());
        resume.getApplicant().setId(applicantId);
        resumeDao.createResume(resume);
        log.debug("Резюме: создано (name={}, categoryId={})", resume.getName(), resume.getCategory().getId());
        return ResumeEditDto.builder()
                .name(resume.getName())
                .categoryId(resume.getCategory().getId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .build();
    }

    @Override
    public ResponseEntity<?> getResumeById(Long id) {
        Optional<Resume> resume = resumeDao.getResumeById(id);
        if (resume.isPresent()) {
            log.info("Резюме: найдено id={}", id);
            ResumeDto dto = new ResumeDto();
            dto.setId(resume.get().getId());
            dto.setApplicantId(resume.get().getApplicant().getId());
            dto.setName(resume.get().getName());
            dto.setCategoryId(resume.get().getCategory().getId());
            dto.setSalary(resume.get().getSalary());
            dto.setIsActive(resume.get().getIsActive());
            dto.setCreatedDate(resume.get().getCreatedDate());
            dto.setUpdateTime(resume.get().getUpdateTime());
            return ResponseEntity.ok(dto);
        } else {
            log.warn("Резюме: не найдено id={}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public void deleteResumeById(Long id) {
        log.warn("Резюме: удаление id={}", id);
        resumeDao.deleteResumeById(id);
    }

    @Override
    public void updateTimeOwned(Long resumeId, Long applicantId) {
        log.debug("updateTimeOwned(resumeId={}, applicantId={})", resumeId, applicantId);
        requireResumeOwner(resumeId, applicantId);
        resumeDao.updateTime(resumeId);
        log.info("Время обновления резюме {} изменено владельцем {}", resumeId, applicantId);
    }

    @Override
    public void editResumeOwned(ResumeEditDto dto, Long resumeId, Long applicantId) {
        log.debug("editResumeOwned(resumeId={}, applicantId={})", resumeId, applicantId);
        requireResumeOwner(resumeId, applicantId);
        Resume resume = new Resume();
        resume.setName(dto.getName());
        resume.getCategory().setId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setIsActive(dto.getIsActive());
        resumeDao.editResume(resume, resumeId, applicantId);
        log.info("Резюме {} отредактировано владельцем {}", resumeId, applicantId);
    }

    @Override
    public void resumeIsActiveOwned(Long resumeId, ResumeIsActiveDto dto, Long applicantId) {
        log.debug("resumeIsActiveOwned(resumeId={}, applicantId={})", resumeId, applicantId);
        requireResumeOwner(resumeId, applicantId);
        resumeDao.resumeIsActive(resumeId, dto.getIsActive());
        log.info("Статус активности резюме {} изменён владельцем {} на {}", resumeId, applicantId, dto.getIsActive());
    }

    private void requireResumeOwner(Long resumeId, Long userId) {
        Long ownerId = resumeDao.getOwnerId(resumeId);
        if (!ownerId.equals(userId)) throw new ForbiddenException("Not your resume");
    }

    @Override
    public List<ResumeDto> searchResumes(ResumeSearchDto criteria) {
        log.debug("ResumeService.searchResumes(criteria={})", criteria);
        List<Resume> results = resumeDao.searchResumes(criteria);
        return results.stream().map(r -> {
            ResumeDto dto = new ResumeDto();
            dto.setId(r.getId());
            dto.setApplicantId(r.getApplicant().getId());
            dto.setName(r.getName());
            dto.setCategoryId(r.getCategory().getId());
            dto.setSalary(r.getSalary());
            dto.setIsActive(r.getIsActive());
            dto.setCreatedDate(r.getCreatedDate());
            dto.setUpdateTime(r.getUpdateTime());
            return dto;
        }).toList();
    }
}
