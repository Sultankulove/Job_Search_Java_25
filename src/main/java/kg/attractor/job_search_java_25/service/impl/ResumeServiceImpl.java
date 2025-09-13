package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.mappers.ResumeMapper;
import kg.attractor.job_search_java_25.model.*;
import kg.attractor.job_search_java_25.repository.*;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private final ContactTypeRepository contactTypeRepository;
    private final WorkExperienceInfoRepository workExperienceInfoRepository;
    private final EducationInfoRepository educationInfoRepository;
    private final ContactInfoRepository contactInfoRepository;

    @Override
    public List<ResumeShortDto> getShortResumesList(Long applicantId) {
        return resumeRepository.getAllByApplicant_Id(applicantId)
                .stream().map(ResumeMapper::toShortDto).toList();
    }

    @Override
    public ResponseEntity<?> updateTime(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ForbiddenException("resume not found"));
        if (resume == null) {
            throw new ForbiddenException("resume not found");
        }

        resume.setUpdateTime(LocalDateTime.now());
        resumeRepository.save(resume);

        return ResponseEntity.noContent().build();
    }

    @Override
    public void editResume(ResumeEditDto resumeEditDto, Long resumeId, Long applicantId) {
        log.info("Резюме: редактирование id={}, applicantId={}", resumeId, applicantId);
        Resume resume = new Resume();
        UserProfileDto userProfileDto = userRepository.findById(applicantId)
                .orElseThrow(() -> new NotFoundException("userProfileDto not found"));

        resume.setId(resumeId);
        resume.setApplicant(userProfileDto);
        resume.setName(resumeEditDto.getName());
        resume.getCategory().setId(resumeEditDto.getCategoryId());
        resume.setSalary(resumeEditDto.getSalary());
        resume.setActive(resumeEditDto.isActive());


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
    public ResumeEditDto saveResume(Long applicantId, ResumeEditDto dto) {
        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException("categoryId is required");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена: id=" + dto.getCategoryId()));

        UserProfileDto applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: id=" + applicantId));

        var resume = new Resume();
        resume.setName(dto.getName());
        resume.setSalary(dto.getSalary());
        resume.setActive(Boolean.TRUE.equals(dto.isActive()));
        resume.setCategory(category);
        resume.setApplicant(applicant);
        resume.setCreatedDate(LocalDateTime.now());
        resume.setUpdateTime(LocalDateTime.now());
        resume = resumeRepository.save(resume);

        log.debug("Резюме: создано id={}, name='{}', categoryId={}",
                resume.getId(), resume.getName(), resume.getCategory().getId());

        if (dto.getWorkExperiences() != null) {
            for (WorkExperienceInfoDto workDto : dto.getWorkExperiences()) {
                WorkExperienceInfo work = new WorkExperienceInfo();
                work.setResume(resume);
                work.setCompanyName(workDto.getCompanyName());
                work.setPosition(workDto.getPosition());
                work.setResponsibilities(workDto.getResponsibilities());
                work.setYears(workDto.getYears());
                workExperienceInfoRepository.save(work);
            }
        }

        if (dto.getEducationInfos() != null) {
            for (EducationInfoDto eduDto : dto.getEducationInfos()) {
                EducationInfo edu = new EducationInfo();
                edu.setResume(resume);
                edu.setInstitution(eduDto.getInstitution());
                edu.setProgram(eduDto.getProgram());
                edu.setDegree(eduDto.getDegree());
                edu.setStartDate(eduDto.getStartDate() != null ? Date.valueOf(eduDto.getStartDate()) : null);
                edu.setEndDate(eduDto.getEndDate() != null ? Date.valueOf(eduDto.getEndDate()) : null);
                educationInfoRepository.save(edu);
            }
        }

        if (dto.getContactInfos() != null) {
            for (ContactInfoDto contactDto : dto.getContactInfos()) {
                if (contactDto.getContactValue() == null || contactDto.getContactValue().isBlank()) {
                    continue;
                }

                ContactType contactType = contactTypeRepository.findById(contactDto.getType_id())
                        .orElseThrow(() -> new IllegalArgumentException("ContactType не найден: id=" + contactDto.getType_id()));

                ContactInfo contact = new ContactInfo();
                contact.setResume(resume);
                contact.setType(contactType);
                contact.setContactValue(contactDto.getContactValue());
                contactInfoRepository.save(contact);
            }
        }

        log.debug("Резюме и вложенные сущности сохранены: resumeId={}", resume.getId());


        return ResumeEditDto.builder()
                .name(resume.getName())
                .categoryId(resume.getCategory().getId())
                .salary(resume.getSalary())
                .active(resume.getIsActive())
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
        resumeRepository.deleteById(id);
    }

    @Override
    public void updateTimeOwned(Long resumeId, Long applicantId) {
        requireResumeOwner(resumeId, applicantId);

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Resume not found"));
        resume.setUpdateTime(LocalDateTime.now());
        resumeRepository.save(resume);}

    @Override
    public void editResumeOwned(ResumeEditDto dto, Long resumeId, Long applicantId) {
        log.debug("editResumeOwned(resumeId={}, applicantId={})", resumeId, applicantId);
        requireResumeOwner(resumeId, applicantId);
        Resume resume = new Resume();
        resume.setId(resumeId);
        UserProfileDto userProfileDto = userRepository.findById(applicantId).orElse(null);
        resume.setApplicant(userProfileDto);
        resume.setName(dto.getName());
        resume.getCategory().setId(dto.getCategoryId());
        resume.setSalary(dto.getSalary());
        resume.setActive(dto.isActive());

        resumeRepository.save(resume);
        log.info("Резюме {} отредактировано владельцем {}", resumeId, applicantId);
    }

    @Override
    public void resumeIsActiveOwned(Long resumeId, ResumeIsActiveDto dto, Long applicantId) {
        requireResumeOwner(resumeId, applicantId);
        resumeRepository.resumeIsActive(resumeId, dto.getIsActive());
    }

    private void requireResumeOwner(Long resumeId, Long userId) {
        Long ownerId = resumeRepository.getOwnerId(resumeId);
        if (!ownerId.equals(userId)) throw new ForbiddenException("Not your resume");
    }

    @Override
    public List<ResumeDto> searchResumes(ResumeSearchDto criteria) {
        log.debug("ResumeService.searchResumes(criteria={})", criteria);
        List<Resume> results = resumeDao.searchResumes(criteria);
        return results.stream().map(ResumeMapper::toDto).toList();
    }

    @Override
    public List<ResumeListViewDto> findAllForList(Long applicantId) {
        return ResumeMapper.toListViewDtoList(resumeRepository.getAllByApplicant_Id(applicantId));
    }

    @Override
    public List<ResumeDto> findResumesById(Long applicantId) {
        return ResumeMapper.toDtoList(resumeRepository.findAllByApplicant_Id(applicantId));
    }

    @Override
    public List<ResumeDto> findAll() {
        return ResumeMapper.toDtoList(resumeRepository.findAll());
    }

    @Override
    public List<ResumeDto> findByCategory(Long categoryId) {
        List<ResumeDto> resume = findResumesById(categoryId);
        return resume;
    }

    @Override
    public List<ResumeDto> findByAuthor(Long userId) {
        return ResumeMapper.toDtoList(resumeRepository.findAllByApplicant_Id(userId));
    }

    @Override
    public Page<ResumeDto> getResumes(Pageable pageble) {
        return resumeRepository.findAll(pageble)
                .map(ResumeMapper::toDto);
    }

    @Override
    public Page<ResumeDto> getResumesByCategory(Long categoryId, PageRequest of) {
        return resumeRepository.findByCategory_Id(categoryId, of)
                .map(ResumeMapper::toDto);
    }

    @Override
    public Page<ResumeDto> getResumesByAuthor(Long userId, Pageable pageable) {
        return resumeRepository.findAllByApplicant_Id(userId, pageable)
                .map(ResumeMapper::toDto);
    }

    @Override
    public Page<ResumeDto> getResumesByAuthorAndCategory(Long userId, Long categoryId, Pageable pageable) {
        return resumeRepository.findAllByApplicant_IdAndCategory_Id(userId, categoryId, pageable)
                .map(ResumeMapper::toDto);
    }

}
