package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancySearchDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyViewDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.mappers.RespondedApplicantMapper;
import kg.attractor.job_search_java_25.mappers.VacancyMapper;
import kg.attractor.job_search_java_25.model.*;
import kg.attractor.job_search_java_25.repository.*;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final ResumeRepository resumeRepository;
    private final VacancyMapper vacancyMapper;
    private final RespondedApplicantMapper respondedApplicantMapper;

    @Override
    public Page<VacancyListItemDto> getVacancies(Pageable pageable) {
        return vacancyRepository.findList(null, pageable);
    }

    @Override
    public Page<VacancyListItemDto> getVacanciesByCategory(Long categoryId, Pageable pageable) {
        return vacancyRepository.findByCategory_Id(categoryId, pageable)
                .map(vacancyMapper::toListItem);
    }

    @Override
    public Page<VacancyListItemDto> findByEmployerId(Long employerId, Pageable pageable) {
        return vacancyRepository.findList(employerId, pageable);
    }

    @Override
    public Page<VacancyListItemDto> findByEmployerIdAndCategory(Long employerId, Long categoryId, Pageable pageable) {
        return vacancyRepository.findByAuthor_IdAndCategory_Id(employerId, categoryId, pageable)
                .map(vacancyMapper::toListItem);
    }

    @Override
    public List<VacancyListItemDto> search(VacancySearchDto c) {
        if (c.getMinSalary()!=null && c.getMaxSalary()!=null && c.getMinSalary() > c.getMaxSalary())
            throw new IllegalArgumentException("minSalary > maxSalary");
        if (c.getExpFrom()!=null && c.getExpTo()!=null && c.getExpFrom() > c.getExpTo())
            throw new IllegalArgumentException("expFrom > expTo");

        int page = c.getPage() == null ? 0  : Math.max(0, c.getPage());
        int size = c.getSize() == null ? 10 : Math.max(1, c.getSize());
        Sort sort = resolveSort(c.getSort());

        Pageable p = PageRequest.of(page, size, sort);

        return vacancyRepository.search(
                        c.getCategoryId(),
                        c.getActiveOnly(),
                        c.getMinSalary(),
                        c.getMaxSalary(),
                        c.getExpFrom(),
                        c.getExpTo(),
                        normalize(c.getTerm()),
                        p)
                .map(vacancyMapper::toListItem)
                .getContent();
    }

    private Sort resolveSort(String s) {
        if (s == null || s.isBlank()) return Sort.by(Sort.Direction.DESC, "updateTime");
        boolean desc = s.startsWith("-");
        String field = desc ? s.substring(1) : s;
        return switch (field) {
            case "updateTime", "salary", "name" -> Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, field);
            default -> Sort.by(Sort.Direction.DESC, "updateTime");
        };
    }
    private String normalize(String term) {
        return (term == null || term.isBlank()) ? null : term.trim();
    }

    @Override
    public Page<VacancyListItemDto> findList(Long authorId, Pageable pageable) {
        return vacancyRepository.findList(authorId, pageable);
    }

    @Override
    @Transactional
    public void touchOwned(Long id, Long authorId) {
        requireOwner(id, authorId);
        if (vacancyRepository.touch(id) == 0) throw new NotFoundException("Vacancy not found");
    }


    @Override
    @Transactional
    public void editVacancyOwned(VacancyUpsertDto dto, Long vacancyId, Long authorId) {
        Vacancy v = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));
        if (!v.getAuthor().getId().equals(authorId)) throw new ForbiddenException("Not your vacancy");

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        vacancyMapper.applyUpsert(dto, v, category, v.getAuthor());
    }

    @Override
    @Transactional
    public void setActiveOwned(Long vacancyId, ActiveDto activeDto, Long authorId) {
        requireOwner(vacancyId, authorId);
        int updated = vacancyRepository.setActive(vacancyId, activeDto.getActive());
        if (updated == 0) throw new NotFoundException("Vacancy not found");
    }

    @Override
    @Transactional
    public VacancyViewDto create(VacancyUpsertDto dto, Long authorId) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Vacancy entity = vacancyMapper.applyUpsert(dto, new Vacancy(), category, author);
        entity = vacancyRepository.save(entity);
        return vacancyMapper.toView(entity);
    }

    @Override
    public VacancyViewDto getById(Long id) {
        Vacancy v = vacancyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));
        return vacancyMapper.toView(v);
    }

    @Override
    @Transactional
    public void deleteOwned(Long id, Long authorId) {
        requireOwner(id, authorId);
        if (!vacancyRepository.existsById(id)) throw new NotFoundException("Vacancy not found");
        vacancyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void respondToVacancy(ResponseDto dto, Long applicantId) {
        Vacancy vac = vacancyRepository.findById(dto.getVacancyId())
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));

        Resume res = resumeRepository.findById(dto.getResumeId())
                .orElseThrow(() -> new NotFoundException("Resume not found"));

        if (!res.getApplicant().getId().equals(applicantId))
            throw new ForbiddenException("Not your resume");

        if (vac.getAuthor() != null && vac.getAuthor().getId().equals(applicantId))
            throw new ForbiddenException("You cannot respond to your own vacancy");

        boolean exists = respondedApplicantRepository
                .existsByVacancy_IdAndResume_Id(vac.getId(), res.getId());
        if (exists) return;

        var entity = respondedApplicantMapper.toEntity(res, vac, false);
        respondedApplicantRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RespondedApplicantDto> getResponsesByVacancy(Long vacancyId, Long employerId) {
        Long ownerId = vacancyRepository.getOwnerId(vacancyId);
        if (ownerId == null) throw new NotFoundException("Vacancy not found");
        if (!ownerId.equals(employerId)) throw new ForbiddenException("Not your vacancy");

        return respondedApplicantMapper.toDtoList(
                respondedApplicantRepository.getResponsesByVacancy(vacancyId)
        );
    }

    private void requireOwner(Long vacancyId, Long authorId) {
        Long ownerId = vacancyRepository.getOwnerId(vacancyId);
        if (ownerId == null) throw new NotFoundException("Vacancy not found");
        if (!ownerId.equals(authorId)) throw new ForbiddenException("Not your vacancy");
    }
}
