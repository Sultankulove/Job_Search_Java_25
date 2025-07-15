package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.ApplicantDto;
import kg.attractor.job_search_java_25.dto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {
    @Override
    public ResponseEntity<List<RespondedApplicantDto>> findRespondedApplicants() {
        // TODO Должен возвращать список откликнувшихся соискателей на вакансию
        return null;
    }

    @Override
    public ResponseEntity<ApplicantDto> findApplicantByName(String name) {
        // TODO Должен возвращать соискателя по имени
        return null;
    }
}
