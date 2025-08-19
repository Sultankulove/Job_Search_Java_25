package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.model.Category;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.repository.CategoryRepository;
import kg.attractor.job_search_java_25.repository.ResumeRepository;
import kg.attractor.job_search_java_25.repository.UserRepository;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final ResumeRepository resumeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public List<ResumeShortDto> getShortResumesList(Long applicantId) {
        log.debug("ResumeService.getShortResumesList(applicantId={})", applicantId);
        List<ResumeShortDto> shortResumes;
        shortResumes = resumeRepository.findAllByApplicant_Id(applicantId)
                        .stream()
                                .map(r -> new ResumeShortDto(r.getName(), r.getUpdateTime()))
                .toList();
        log.info("Резюме: короткий список size={}", shortResumes.size());
        return shortResumes;
    }

    @Override
    public ResponseEntity<?> updateTime(Long resumeId) {
        log.info("Резюме: обновление времени id={}", resumeId);
        Resume resume = resumeRepository.findById(resumeId).orElse(null);
        if (resume == null) {
            System.out.println("updateTime resumeId=" + resumeId + "not found");
            return ResponseEntity.notFound().build();
        }
        resume.setUpdateTime(LocalDateTime.now());
        resumeRepository.save(resume);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void editResume(ResumeEditDto resumeEditDto, Long resumeId, Long applicantId) {
        log.info("Резюме: редактирование id={}, applicantId={}", resumeId, applicantId);
        Resume resume = new Resume();
        User user = userRepository.findById(applicantId).orElse(null);
        resume.setId(resumeId);
        resume.setApplicant(user);
        resume.setName(resumeEditDto.getName());
        resume.getCategory().setId(resumeEditDto.getCategoryId());
        resume.setSalary(resumeEditDto.getSalary());
        resume.setActive(resumeEditDto.getActive());


        resumeRepository.save(resume);
        log.debug("Резюме: отредактировано id={}", resumeId);
    }

    @Override
    public void resumeIsActiveById(Long resumeId, ResumeIsActiveDto resumeIsActiveDto) {
        boolean isActive = resumeIsActiveDto.getIsActive();
        log.info("Резюме: публикация id={}, isActive={}", resumeId, isActive);
        resumeRepository.resumeIsActive(resumeId, isActive);
    }

    @Override
    @Transactional
    public ResumeEditDto createResume(Long applicantId, ResumeEditDto dto) {
        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException("categoryId is required");
        }

        log.info("Резюме: создание applicantId={}", applicantId);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена: id=" + dto.getCategoryId()));
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: id=" + applicantId));

        LocalDateTime now = LocalDateTime.now();

        var resume = new Resume();
        resume.setName(dto.getName());
        resume.setSalary(dto.getSalary());
        resume.setActive(Boolean.TRUE.equals(dto.getActive()));
        resume.setCategory(category);
        resume.setApplicant(applicant);
        resume.setCreatedDate(now);
        resume.setUpdateTime(now);
        resume = resumeRepository.save(resume);

        log.debug("Резюме: создано id={}, name='{}', categoryId={}",
                resume.getId(), resume.getName(), resume.getCategory().getId());

        return ResumeEditDto.builder()
                .name(resume.getName())
                .categoryId(resume.getCategory() != null ? resume.getCategory().getId() : null)
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .build();
    }

    @Override
    public ResponseEntity<?> getResumeById(Long id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        if (resume.isPresent()) {
            log.info("Резюме: найдено id={}", id);
            ResumeDto dto = new ResumeDto();
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
        resumeRepository.deleteById(id);
    }

    @Override
    public void updateTimeOwned(Long resumeId, Long applicantId) {
        log.debug("updateTimeOwned(resumeId={}, applicantId={})", resumeId, applicantId);
        requireResumeOwner(resumeId, applicantId);
        Resume resume = resumeRepository.findById(resumeId).orElse(null);
        if (resume == null) {
            log.error("updateTimeOwned resumeId={}not found", resumeId);
        }
        resume.setUpdateTime(LocalDateTime.now());
        resumeRepository.save(resume);
        log.info("Время обновления резюме {} изменено владельцем {}", resumeId, applicantId);
    }

    @Override
    public void editResumeOwned(ResumeEditDto dto, Long resumeId, Long applicantId) {
        log.debug("editResumeOwned(resumeId={}, applicantId={})", resumeId, applicantId);
        requireResumeOwner(resumeId, applicantId);
        Resume resume = new Resume();
        resume.setId(resumeId);
        User user = userRepository.findById(applicantId).orElse(null);
        resume.setApplicant(user);
        resume.setName(dto.getName());
        resume.getCategory().setId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setActive(dto.getActive());

        resumeRepository.save(resume);
        log.info("Резюме {} отредактировано владельцем {}", resumeId, applicantId);
    }

    @Override
    public void resumeIsActiveOwned(Long resumeId, ResumeIsActiveDto dto, Long applicantId) {
        log.debug("resumeIsActiveOwned(resumeId={}, applicantId={})", resumeId, applicantId);
        requireResumeOwner(resumeId, applicantId);
        resumeRepository.resumeIsActive(resumeId, dto.getIsActive());
        log.info("Статус активности резюме {} изменён владельцем {} на {}", resumeId, applicantId, dto.getIsActive());
    }

    private void requireResumeOwner(Long resumeId, Long userId) {
        Long ownerId = resumeRepository.getOwnerId(resumeId);
        if (!ownerId.equals(userId)) throw new ForbiddenException("Not your resume");
    }

    @Override
    public List<ResumeDto> searchResumes(ResumeSearchDto criteria) {
        log.debug("ResumeService.searchResumes(criteria={})", criteria);
        List<Resume> results = resumeDao.searchResumes(criteria);
        return results.stream().map(r -> {
            ResumeDto dto = new ResumeDto();
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

    @Override
    public List<ResumeListViewDto> findAllForList(Long applicantId) {
        log.debug("ResumeService.findAllForList(applicantId={})", applicantId);

        return resumeRepository.findAllForList(applicantId)
                .stream().map(v -> {
            ResumeListViewDto dto = new ResumeListViewDto();
            dto.setApplicantId(v.getApplicantId());
            dto.setCategoryId(v.getCategoryId());
            dto.setName(v.getName());
            dto.setSalary(v.getSalary());
            dto.setIsActive(Boolean.TRUE.equals(v.getIsActive()));
            dto.setCreatedDate(v.getCreatedDate());
            dto.setUpdateTime(v.getUpdateTime());
                    return dto;
                }).toList();
//        return resumeRepository.findAllForList(applicantId).stream().map(v -> {
//            ResumeDto dto = new ResumeDto();
//            dto.setApplicantId(v.getApplicantId());
//            dto.setCategoryId(v.getCategoryId());
//            dto.setName(v.getName());
//            dto.setSalary(v.getSalary());
//            dto.setActive(Boolean.TRUE.equals(v.getIsActive()));
//            dto.setCreatedDate(v.getCreatedDate());
//            dto.setUpdateTime(v.getUpdateTime());
//            return dto;
//        }).toList();
    }

    @Override
    public List<ResumeDto> findResumesById(Long applicantId) {
        return resumeRepository.findAllByApplicant_Id(applicantId)
                .stream().map(r -> {
                    ResumeDto dto = new ResumeDto();
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

    @Override
    public List<ResumeDto> findAll() {

        List<ResumeDto> resumes = resumeRepository.findAllResumes();
//                    ResumeDto dto = new ResumeDto();
//        List<ResumeDto> resumes = resumeRepository.findAll()
//                .stream()
//                .map(r -> {
//                    dto.setApplicantId(r.getApplicant().getId());
//                    dto.setName(r.getName());
//                    dto.setCategoryId(r.getCategory().getId());
//                    dto.setSalary(r.getSalary());
//                    dto.setIsActive(r.getIsActive());
//                    dto.setCreatedDate(r.getCreatedDate());
//                    dto.setUpdateTime(r.getUpdateTime());
//                    return dto;
//                }).toList();

        log.debug("resumes: {}", resumes);
        return resumes;

    }

    @Override
    public List<ResumeDto> findByCategory(Long categoryId) {
        List<ResumeDto> resume = findResumesById(categoryId);
        return resume;
    }

    @Override
    public List<ResumeDto> findByAuthor(Long userId) {
        return resumeRepository.findAllByApplicant_Id(userId)
                .stream()
                .map(r -> {
                    ResumeDto dto = new ResumeDto();
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
