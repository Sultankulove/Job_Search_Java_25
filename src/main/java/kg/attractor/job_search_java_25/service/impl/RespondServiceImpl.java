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
    public boolean alreadyResponded(Long userId, Long vacancyId) {
        return resumeRepository
                .findFirstByApplicantIdAndIsActiveTrueOrderByUpdateTimeDesc(userId)
                .map(r -> respondedApplicantRepository.findByVacancyIdAndResumeId(vacancyId, r.getId()).isPresent())
                .orElse(false);
    }

    @Override
    @Transactional
    public void respond(Long userId, Long vacancyId) {

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Вакансия не найдена"));

        if (vacancy.getAuthor() != null && vacancy.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("Нельзя откликаться на собственную вакансию");
        }


        User applicant = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Resume resume = resumeRepository
                .findFirstByApplicantIdAndIsActiveTrueOrderByUpdateTimeDesc(applicant.getId())
                .orElseThrow(() -> new NotFoundException("У вас нет активного резюме"));


        respondedApplicantRepository.findByVacancyIdAndResumeId(vacancyId, resume.getId())
                .ifPresent(ra -> { throw new ForbiddenException("Вы уже откликались на эту вакансию"); });


        RespondedApplicant ra = new RespondedApplicant();
        ra.setVacancy(vacancy);
        ra.setResume(resume);
        ra.setConfirmation(null);

        respondedApplicantRepository.save(ra);
    }

    @Override
    public List<ResponseDto> listResponsesForVacancy(Long vacancyId) {

        Vacancy v = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Вакансия не найдена"));

        return respondedApplicantRepository.findAllByVacancyId(v.getId())
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<ResponseDto> listResponsesForApplicant(Long userId) {
        List<Long> resumeIds = resumeRepository.findAllByApplicantId(userId)
                .stream().map(Resume::getId).toList();

        if (resumeIds.isEmpty()) return List.of();

        return respondedApplicantRepository.findAllByResumeIdIn(resumeIds)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ResponseDto toDto(RespondedApplicant ra) {
        return ResponseDto.builder()
                .id(ra.getId())
                .resumeId(ra.getResume() != null ? ra.getResume().getId() : null)
                .vacancyId(ra.getVacancy() != null ? ra.getVacancy().getId() : null)
                .resumeName(ra.getResume() != null ? ra.getResume().getName() : null)
                .vacancyName(ra.getVacancy() != null ? ra.getVacancy().getName() : null)
                .applicantName(
                        ra.getResume() != null && ra.getResume().getApplicant() != null
                                ? ra.getResume().getApplicant().getName()
                                : null
                )
                .confirmation(ra.getConfirmation())
                .createdDate(ra.getCreatedDate())
                .build();
    }

}
