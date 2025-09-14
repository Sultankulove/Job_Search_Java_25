package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeViewDto;
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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ContactTypeRepository contactTypeRepository;
    private final ResumeMapper resumeMapper;

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
        Category category  = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Resume entity = new Resume();
        resumeMapper.applyUpsert(dto, entity, category, applicant, contactTypeId ->
                contactTypeRepository.findById(contactTypeId)
                        .orElseThrow(() -> new NotFoundException("ContactType not found"))
        );
        entity = resumeRepository.save(entity);
        return resumeMapper.toView(entity);
    }

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
}
