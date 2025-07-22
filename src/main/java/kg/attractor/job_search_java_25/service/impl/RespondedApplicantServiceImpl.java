package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespondedApplicantServiceImpl implements RespondedApplicantService {

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getVacancyRespondedApplicant(Long vacancyId) {
        return null;
    }
}
