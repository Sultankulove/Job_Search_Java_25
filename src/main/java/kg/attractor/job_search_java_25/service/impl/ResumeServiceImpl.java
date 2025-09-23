package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.CategoryDtos.CategoryDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeAllInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeViewDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactInfoDto;
import kg.attractor.job_search_java_25.dto.userDtos.AccountType;
import kg.attractor.job_search_java_25.dto.userDtos.UserViewProfileDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.mappers.ResumeMapper;
import kg.attractor.job_search_java_25.model.*;
import kg.attractor.job_search_java_25.repository.*;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final ResumeMapper resumeMapper;
    private final EducationInfoRepository educationInfoRepository;
    private final WorkExperienceInfoRepository workExperienceInfoRepository;
    private final ContactInfoRepository contactInfoRepository;


    @Override
    public Page<ResumeListItemDto> getResumesByAuthorAndCategory(
            Long applicantId, Long categoryId, Pageable pageable,
            BigDecimal salaryFrom, BigDecimal salaryTo) {

        if (salaryFrom != null && salaryTo != null && salaryFrom.compareTo(salaryTo) > 0) {
            var t = salaryFrom; salaryFrom = salaryTo; salaryTo = t;
        }
        return resumeRepository.findList(applicantId, categoryId, salaryFrom, salaryTo, pageable);
    }
    @Override
    public Page<ResumeListItemDto> getResumesByAuthor(
            Long applicantId, Pageable pageable,
            BigDecimal salaryFrom, BigDecimal salaryTo) {

        if (salaryFrom != null && salaryTo != null && salaryFrom.compareTo(salaryTo) > 0) {
            var t = salaryFrom; salaryFrom = salaryTo; salaryTo = t;
        }
        return resumeRepository.findList(applicantId, null, salaryFrom, salaryTo, pageable);
    }

    @Override
    public Page<ResumeListItemDto> getResumesByCategory(
            Long categoryId, Pageable pageable,
            BigDecimal salaryFrom, BigDecimal salaryTo) {

        if (salaryFrom != null && salaryTo != null && salaryFrom.compareTo(salaryTo) > 0) {
            var t = salaryFrom; salaryFrom = salaryTo; salaryTo = t;
        }
        return resumeRepository.findList(null, categoryId, salaryFrom, salaryTo, pageable);
    }

    @Override
    public Page<ResumeListItemDto> getResumes(
            Pageable pageable,
            BigDecimal salaryFrom, BigDecimal salaryTo) {

        if (salaryFrom != null && salaryTo != null && salaryFrom.compareTo(salaryTo) > 0) {
            var t = salaryFrom; salaryFrom = salaryTo; salaryTo = t;
        }
        return resumeRepository.findList(null, null, salaryFrom, salaryTo, pageable);
    }




    @Override
    @Transactional
    public void deleteOwned(Long resumeId, Long ownerId) {
        Long actualOwner = resumeRepository.getOwnerId(resumeId);
        if (actualOwner == null) throw new NotFoundException("Resume not found");
        if (!actualOwner.equals(ownerId)) throw new ForbiddenException("Not your resume");
        resumeRepository.deleteById(resumeId);
    }

    @Override
    public Page<ResumeListItemDto> getResumesByAuthorAndCategory(Long applicantId, Long categoryId, Pageable pageable) {
        return resumeRepository
                .findAllByApplicant_IdAndCategory_Id(applicantId, categoryId, pageable)
                .map(resumeMapper::toListItem);
    }


    @Override
    public Page<ResumeListItemDto> getResumesByAuthor(Long applicantId, Pageable pageable) {
        log.debug("getResumesByAuthor(applicantId={}, pageable={})", applicantId, pageable);
        return resumeRepository.findList(applicantId, pageable);
    }





    @Override
    public Page<ResumeListItemDto> getResumes(Pageable pageable) {
        return resumeRepository.findList(null, pageable);
    }

    @Override
    public Page<ResumeListItemDto> getResumesByCategory(Long categoryId, Pageable pageable) {
        return resumeRepository.findByCategory_Id(categoryId, pageable).map(resumeMapper::toListItem);
    }

    @Override
    public ResumeViewDto getResumeById(Long id) {
        Resume r = resumeRepository.findById(id).orElseThrow(() -> new NotFoundException("Resume not found"));
        return resumeMapper.toView(r);
    }

    @Transactional
    @Override
    public ResumeViewDto saveResume(Long applicantId, ResumeUpsertDto dto) {
        Category category = categoryRepository.findById(
                        Objects.requireNonNull(dto.getCategoryId(), "categoryId must not be null"))
                .orElseThrow(() -> new NotFoundException("Category not found"));

        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Resume resume = new Resume();
        resume.setName(trimToNull(dto.getName()));
        resume.setSalary(dto.getSalary());
        resume.setActive(Boolean.TRUE.equals(dto.isActive()));
        resume.setCategory(category);
        resume.setApplicant(applicant);


        resume.setWorkExperiences(new ArrayList<>());
        if (dto.getWorkExperiences() != null) {
            for (WorkExperienceInfoDto w : dto.getWorkExperiences()) {
                if (isEmptyWork(w)) continue;

                WorkExperienceInfo e = new WorkExperienceInfo();
                e.setCompanyName(trimToNull(w.getCompanyName()));
                e.setPosition(trimToNull(w.getPosition()));
                e.setYears(clamp(w.getYears(), 0, 80));
                e.setResponsibilities(trimToNull(w.getResponsibilities()));
                e.setResume(resume);
                resume.getWorkExperiences().add(e);
            }
        }


        resume.setEducationInfos(new ArrayList<>());
        if (dto.getEducationInfos() != null) {
            for (EducationInfoDto ed : dto.getEducationInfos()) {
                if (isEmptyEducation(ed)) continue;

                EducationInfo e = new EducationInfo();
                e.setInstitution(trimToNull(ed.getInstitution()));
                e.setDegree(trimToNull(ed.getDegree()));
                e.setProgram(trimToNull(ed.getProgram()));

                LocalDate start = ed.getStartDate();
                LocalDate end   = ed.getEndDate();

                if (start != null && end != null && end.isBefore(start)) {
                    LocalDate t = start; start = end; end = t;
                }
                e.setStartDate(start);
                e.setEndDate(end);

                e.setResume(resume);
                resume.getEducationInfos().add(e);
            }
        }


        resume.setContactInfos(new ArrayList<>());

        List<ContactInfoDto> contacts = dto.getContactInfos();
        if (contacts != null && !contacts.isEmpty()) {
            for (int i = 0; i < contacts.size(); i++) {
                ContactInfoDto c = contacts.get(i);
                if (c == null) {
                    continue;
                }

                String value = trimToNull(c.getContactValue());
                Long typeId = c.getTypeId();


                if (value == null && typeId == null) {
                    continue;
                }

                if (typeId == null) {
                    log.debug("Skip contact row #{}: value present but typeId is null", i);
                    continue;
                }

                var typeOpt = contactTypeRepository.findById(typeId);
                if (typeOpt.isEmpty()) {
                    log.debug("Skip contact row #{}: unknown contactTypeId={}", i, typeId);
                    continue;
                }

                ContactInfo ci = new ContactInfo();
                ci.setContactValue(value);
                ci.setType(typeOpt.get());
                ci.setResume(resume);

                resume.getContactInfos().add(ci);
            }
        }

        resume = resumeRepository.save(resume);
        return resumeMapper.toView(resume);
    }



    private static boolean isBlank(String s) { return s == null || s.trim().isBlank(); }

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static boolean isEmptyWork(WorkExperienceInfoDto w) {
        return w == null
                || (isBlank(w.getCompanyName())
                && isBlank(w.getPosition())
                && w.getYears() == null
                && isBlank(w.getResponsibilities()));
    }

    private static boolean isEmptyEducation(EducationInfoDto ed) {
        return ed == null
                || (isBlank(ed.getInstitution())
                && isBlank(ed.getDegree())
                && isBlank(ed.getProgram())
                && ed.getStartDate() == null
                && ed.getEndDate() == null);
    }

    private static Integer clamp(Integer val, int min, int max) {
        if (val == null) return null;
        return Math.max(min, Math.min(max, val));
    }

    private static BigDecimal normalizeSalary(BigDecimal salary) {
        if (salary == null || salary.signum() <= 0) return null;
        return salary.setScale(2, RoundingMode.DOWN);
    }


//    @Transactional
//    @Override
//    public ResumeViewDto saveResume(Long applicantId, ResumeUpsertDto dto) {
//        Category category = categoryRepository.findById(
//                        Objects.requireNonNull(dto.getCategoryId(), "categoryId must not be null"))
//                .orElseThrow(() -> new NotFoundException("Category not found"));
//        User applicant = userRepository.findById(applicantId)
//                .orElseThrow(() -> new NotFoundException("User not found"));
//
//        Resume resume = new Resume();
//        resume.setName(dto.getName());
//        resume.setSalary(dto.getSalary());
//        resume.setActive(Boolean.TRUE.equals(dto.getActive()));
//        resume.setCategory(category);
//        resume.setApplicant(applicant);
//
//
//        resume.setWorkExperiences(new ArrayList<>());
//        if (dto.getWorkExperiences() != null) {
//            for (WorkExperienceInfoDto w : dto.getWorkExperiences()) {
//                if (w == null || w.getCompanyName() == null || w.getCompanyName().isBlank()) continue;
//
//                WorkExperienceInfo e = new WorkExperienceInfo();
//                e.setCompanyName(w.getCompanyName());
//                e.setPosition(w.getPosition());
//                e.setYears(w.getYears());
//                e.setResponsibilities(w.getResponsibilities());
//                e.setResume(resume);
//                resume.getWorkExperiences().add(e);
//            }
//        }
//
//
//        resume.setEducationInfos(new ArrayList<>());
//        if (dto.getEducationInfos() != null) {
//            for (EducationInfoDto ed : dto.getEducationInfos()) {
//                if (ed == null || ed.getInstitution() == null || ed.getInstitution().isBlank()) continue;
//
//                EducationInfo e = new EducationInfo();
//                e.setInstitution(ed.getInstitution());
//                e.setDegree(ed.getDegree());
//                e.setProgram(ed.getProgram());
//                if (ed.getStartDate() != null) e.setStartDate(Date.valueOf(ed.getStartDate()));
//                if (ed.getEndDate() != null)   e.setEndDate(Date.valueOf(ed.getEndDate()));
//                e.setResume(resume);
//                resume.getEducationInfos().add(e);
//            }
//        }
//
//
//        resume.setContactInfos(new ArrayList<>());
//        if (dto.getContactInfos() != null) {
//            for (ContactInfoDto c : dto.getContactInfos()) {
//                if (c == null || c.getContactValue() == null || c.getContactValue().isBlank()) continue;
//
//                ContactInfo ci = new ContactInfo();
//                ci.setContactValue(c.getContactValue());
//                if (c.getTypeId() != null) {
//                    ContactType type = contactTypeRepository.findById(c.getTypeId())
//                            .orElseThrow(() -> new NotFoundException("ContactType not found"));
//                    ci.setType(type);
//                }
//                ci.setResume(resume);
//                resume.getContactInfos().add(ci);
//            }
//        }
//
//        resume = resumeRepository.save(resume);
//
//        return resumeMapper.toView(resume);
//    }



    @Transactional
    @Override
    public void editResumeOwned(ResumeUpsertDto dto, Long resumeId, Long applicantId) {
        requireResumeOwner(resumeId, applicantId);
        Resume r = resumeRepository.findById(resumeId).orElseThrow(() -> new NotFoundException("Resume not found"));
        Category cat = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        resumeMapper.applyUpsert(dto, r, cat, null, contactTypeId ->
                contactTypeRepository.findById(contactTypeId)
                        .orElseThrow(() -> new NotFoundException("ContactType not found"))
        );
    }

    @Transactional
    @Override
    public void resumeIsActiveOwned(Long resumeId, ActiveDto dto, Long applicantId) {
        requireResumeOwner(resumeId, applicantId);
        int updated = resumeRepository.setActive(resumeId, dto.getActive());
        if (updated == 0) throw new NotFoundException("Resume not found");
    }

    @Transactional
    @Override
    public void updateTimeOwned(Long resumeId, Long applicantId) {
        requireResumeOwner(resumeId, applicantId);
        var r = resumeRepository.findById(resumeId).orElseThrow(() -> new NotFoundException("Resume not found"));
        r.setUpdateTime(LocalDateTime.now());
    }


    @Transactional
    @Override
    public void deleteResumeById(Long id) { resumeRepository.deleteById(id); }

    private void requireResumeOwner(Long resumeId, Long userId) {
        Long ownerId = resumeRepository.getOwnerId(resumeId);
        if (ownerId == null) throw new NotFoundException("Resume not found");
        if (!ownerId.equals(userId)) throw new ForbiddenException("Not your resume");
    }

    @Override
    public void checkResumeOwnership(Long resumeId, Long userId) {
        Long ownerId = resumeRepository.findOwnerIdByResumeId(resumeId)
                .orElseThrow(() -> new NotFoundException("Resume not found: " + resumeId));

        if (!ownerId.equals(userId)) {
            throw new ForbiddenException("Access denied: resume does not belong to current user");
        }
    }

    @Override
    public ResumeUpsertDto getResumeByIdForEdit(Long id) {
        Resume r = resumeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resume not found"));

        List<EducationInfo> educationInfos = educationInfoRepository.getEducationInfosByResume_Id(r.getId());

        List<EducationInfoDto> educationInfoDtos = educationInfos.stream()
                .map(e -> EducationInfoDto.builder()
                        .institution(e.getInstitution())
                        .program(e.getProgram())
                        .degree(e.getDegree())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .build())
                .toList();

        List<WorkExperienceInfo> workExperienceInfos = workExperienceInfoRepository.getWorkExperienceInfosByResume_Id(r.getId());

        List<WorkExperienceInfoDto> workExperienceInfoDtos = workExperienceInfos.stream()
                .map(w -> WorkExperienceInfoDto.builder()
                        .companyName(w.getCompanyName())
                        .position(w.getPosition())
                        .responsibilities(w.getResponsibilities())
                        .years(w.getYears())
                        .build())
                .toList();

        List<ContactInfo> contactInfos = contactInfoRepository.getContactInfosByResume_Id(r.getId());

        List<ContactInfoDto> contactInfoDtos = contactInfos.stream()
                .map(c -> ContactInfoDto.builder()
                        .typeId(c.getType().getId())
                        .contactValue(c.getContactValue())
                        .build())
                .toList();

        return ResumeUpsertDto.builder()
                .name(r.getName())
                .categoryId(r.getCategory() != null ? r.getCategory().getId() : null)
                .salary(r.getSalary())
                .active(Boolean.TRUE.equals(r.getIsActive()))
                .educationInfos(educationInfoDtos)
                .workExperiences(workExperienceInfoDtos)
                .contactInfos(contactInfoDtos)
                .build();
    }

    @Override
    public ResumeAllInfoDto getResumeAllById(Long id) {
        Resume entity = resumeRepository.getResumeById(id);
        ResumeAllInfoDto dto = new ResumeAllInfoDto();
        User applicant = userRepository.findById(entity.getApplicant().getId())
                .orElseThrow(() -> new NotFoundException("Applicant not found"));

        UserViewProfileDto userDto = new UserViewProfileDto();
        userDto.setId(applicant.getId());
        userDto.setName(applicant.getName());
        userDto.setUsername(applicant.getUsername());
        userDto.setAge(applicant.getAge());
        userDto.setEmail(applicant.getEmail());
        userDto.setEmail(applicant.getEmail());
        userDto.setAvatar(applicant.getAvatar());
        userDto.setAccountType(AccountType.valueOf(applicant.getAccountType()));

        dto.setUser(userDto);
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdateTime(entity.getUpdateTime());
        Category category = categoryRepository.findById(entity.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        dto.setCategory(categoryDto);
        dto.setSalary(entity.getSalary());
        dto.setActive(Boolean.TRUE.equals(entity.getIsActive()));

        List<EducationInfo> educationInfos = educationInfoRepository.getEducationInfosByResume_Id(entity.getId());

        List<EducationInfoDto> educationInfoDtos = educationInfos.stream()
                .map(e -> EducationInfoDto.builder()
                        .institution(e.getInstitution())
                        .program(e.getProgram())
                        .degree(e.getDegree())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .build())
                .toList();

        List<WorkExperienceInfo> workExperienceInfos = workExperienceInfoRepository.getWorkExperienceInfosByResume_Id(entity.getId());

        List<WorkExperienceInfoDto> workExperienceInfoDtos = workExperienceInfos.stream()
                .map(w -> WorkExperienceInfoDto.builder()
                        .companyName(w.getCompanyName())
                        .position(w.getPosition())
                        .responsibilities(w.getResponsibilities())
                        .years(w.getYears())
                        .build())
                .toList();

        List<ContactInfo> contactInfos = contactInfoRepository.getContactInfosByResume_Id(entity.getId());

        List<ContactInfoDto> contactInfoDtos = contactInfos.stream()
                .map(c -> ContactInfoDto.builder()
                        .typeId(c.getType().getId())
                        .contactValue(c.getContactValue())
                        .build())
                .toList();

        dto.setEducationInfos(educationInfoDtos);
        dto.setContactInfos(contactInfoDtos);
        dto.setWorkExperiences(workExperienceInfoDtos);

        return dto;
    }
}
