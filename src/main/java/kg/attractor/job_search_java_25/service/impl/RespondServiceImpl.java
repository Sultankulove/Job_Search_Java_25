package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.repository.RespondedApplicantRepository;
import kg.attractor.job_search_java_25.repository.ResumeRepository;
import kg.attractor.job_search_java_25.repository.UserRepository;
import kg.attractor.job_search_java_25.repository.VacancyRepository;
import kg.attractor.job_search_java_25.service.RespondService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RespondServiceImpl implements RespondService {

    private final RespondedApplicantRepository respondedApplicantRepository;
    private final ResumeRepository resumeRepository;
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    @Override
    public boolean userInResponse(Long userId, Long responseId) {
        RespondedApplicant ra = respondedApplicantRepository.findById(responseId)
                .orElseThrow(() -> new NotFoundException("Response not found"));
        Long applicantId = ra.getResume().getApplicant().getId();
        Long employerId  = ra.getVacancy().getAuthor().getId();
        return userId.equals(applicantId) || userId.equals(employerId);
    }

    @Override
    public boolean alreadyResponded(Long userId, Long vacancyId) {
        return resumeRepository
                .findFirstByApplicantIdAndIsActiveTrueOrderByUpdateTimeDesc(userId)
                .map(resume -> respondedApplicantRepository
                        .findByVacancyIdAndResumeId(vacancyId, resume.getId())
                        .isPresent())
                .orElse(false);
    }

    @Override
    @Transactional
    public Long respond(Long userId, Long vacancyId, Long resumeId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));

        if (vacancy.getAuthor() != null && vacancy.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("You cannot respond to your own vacancy");
        }

        User applicant = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Resume not found"));

        if (!resume.getApplicant().getId().equals(applicant.getId())) {
            throw new ForbiddenException("Not your resume");
        }

        if (!resume.getIsActive()) {
            throw new ForbiddenException("Resume must be active to respond");
        }

        respondedApplicantRepository.findByVacancyIdAndResumeId(vacancyId, resume.getId())
                .ifPresent(ra -> {
                    throw new ForbiddenException("You already responded to this vacancy");
                });

        RespondedApplicant entity = new RespondedApplicant();
        entity.setVacancy(vacancy);
        entity.setResume(resume);
        entity.setConfirmation(null);

        entity = respondedApplicantRepository.save(entity);
        return entity.getId();
    }

    @Override
    public List<ResponseDto> listResponsesForVacancy(Long vacancyId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Vacancy not found"));

        return respondedApplicantRepository.findAllByVacancyId(vacancy.getId())
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<ResponseDto> listResponsesForApplicant(Long userId) {
        List<Long> resumeIds = resumeRepository.findAllByApplicantId(userId)
                .stream()
                .map(Resume::getId)
                .toList();
        if (resumeIds.isEmpty()) {
            return List.of();
        }
        return respondedApplicantRepository.findAllByResumeIdIn(resumeIds)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<ResponseDto> listResponsesForEmployer(Long userId) {
        return respondedApplicantRepository.findAllByVacancy_Author_Id(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ResponseDto toDto(RespondedApplicant entity) {
        return ResponseDto.builder()
                .id(entity.getId())
                .resumeId(entity.getResume() != null ? entity.getResume().getId() : null)
                .vacancyId(entity.getVacancy() != null ? entity.getVacancy().getId() : null)
                .resumeName(entity.getResume() != null ? entity.getResume().getName() : null)
                .vacancyName(entity.getVacancy() != null ? entity.getVacancy().getName() : null)
                .applicantName(
                        entity.getResume() != null && entity.getResume().getApplicant() != null
                                ? entity.getResume().getApplicant().getName()
                                : null
                )
                .confirmation(entity.getConfirmation())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
