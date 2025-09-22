package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantUpsertDto;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.repository.RespondedApplicantRepository;
import kg.attractor.job_search_java_25.repository.ResumeRepository;
import kg.attractor.job_search_java_25.repository.VacancyRepository;
import kg.attractor.job_search_java_25.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl implements RespondedApplicantService {

    private final RespondedApplicantRepository repo;
    private final ResumeRepository resumeRepo;
    private final VacancyRepository vacancyRepo;


    @Override
    @Transactional(readOnly = true)
    public List<RespondedApplicantDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RespondedApplicantDto getById(Long id) {
        var e = repo.findById(id).orElseThrow(() -> new NotFoundException("Response not found"));
        return toDto(e);
    }

    @Override
    public RespondedApplicantDto create(RespondedApplicantUpsertDto dto) {
        var resume = resumeRepo.findById(dto.getResumeId())
                .orElseThrow(() -> new NotFoundException("Resume not found"));
        var vacancy = vacancyRepo.findById(dto.getVacancyId())
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));

        if (repo.existsByVacancy_IdAndResume_Id(vacancy.getId(), resume.getId())) {
            throw new IllegalStateException("Already responded");
        }

        var e = new RespondedApplicant();
        e.setResume(resume);
        e.setVacancy(vacancy);
        e.setCreatedDate(LocalDateTime.now());
        e.setConfirmation(Boolean.TRUE.equals(dto.getConfirmation()));

        e = repo.save(e);
        return toDto(e);
    }

    @Override
    public RespondedApplicantDto update(Long id, RespondedApplicantUpsertDto dto) {
        var e = repo.findById(id).orElseThrow(() -> new NotFoundException("Response not found"));

         if (dto.getResumeId() != null && !dto.getResumeId().equals(e.getResume().getId())) {
             e.setResume(resumeRepo.findById(dto.getResumeId()).orElseThrow(() -> new NotFoundException("Resume not found")));
         }
         if (dto.getVacancyId() != null && !dto.getVacancyId().equals(e.getVacancy().getId())) {
             e.setVacancy(vacancyRepo.findById(dto.getVacancyId()).orElseThrow(() -> new NotFoundException("Vacancy not found")));
         }

        if (dto.getConfirmation() != null) {
            e.setConfirmation(dto.getConfirmation());
        }

        e = repo.save(e);
        return toDto(e);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Response not found");
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean alreadyResponded(Long resumeId, Long vacancyId) {
        return repo.existsByVacancy_IdAndResume_Id(vacancyId, resumeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RespondedApplicantDto> listByVacancy(Long vacancyId) {
        return repo.findAllByVacancy_Id(vacancyId).stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RespondedApplicantDto> listByResume(Long resumeId) {
        return repo.findAllByResume_Id(resumeId).stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countForEmployer(Long employerUserId) {
        return repo.countByVacancy_Author_Id(employerUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countForApplicant(Long applicantUserId) {
        return repo.countByResume_Applicant_Id(applicantUserId);
    }

    private RespondedApplicantDto toDto(RespondedApplicant e) {
        var dto = new RespondedApplicantDto();
        dto.setId(e.getId());
        dto.setCreatedDate(e.getCreatedDate());
        dto.setConfirmation(Boolean.TRUE.equals(e.getConfirmation()));
        if (e.getResume() != null) {
            dto.setResumeId(e.getResume().getId());
            // при желании можно положить и имя соискателя:
            // dto.setApplicantName(e.getResume().getApplicant().getName());
        }
        if (e.getVacancy() != null) {
            dto.setVacancyId(e.getVacancy().getId());
            // dto.setVacancyName(e.getVacancy().getName());
        }
        return dto;
    }
}
