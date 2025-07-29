package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.RespondedApplicantsDao;
import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl implements RespondedApplicantService {
    private final UserDao userDao;
    private final ResumeDao resumeDao;
    private final RespondedApplicantsDao respondedApplicantsDao;

    @Override
    public void respondToVacancy(String email, Long resumeId, Long vacancyId) {
        Optional<Long> userId = userDao.getUserIdByEmail(email);

        resumeDao.getResumeByIdAndUserId(resumeId, userId)
                .orElseThrow(() -> new AccessDeniedException("Этот резюме вам не принадлежит"));

        respondedApplicantsDao.respondedToVacancy(resumeId, vacancyId);

    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getVacancyRespondedApplicant(Long vacancyId) {
        return null;
    }
}
