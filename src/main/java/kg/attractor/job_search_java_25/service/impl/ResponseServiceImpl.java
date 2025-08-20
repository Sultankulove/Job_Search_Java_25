package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.repository.RespondedApplicantRepository;
import kg.attractor.job_search_java_25.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final RespondedApplicantRepository repo;

    @Override
    public long countForEmployer(Long employerId)  {
        return repo.countForEmployer(employerId); }

    @Override
    public long countForApplicant(Long applicantId){
        return repo.countForApplicant(applicantId); }

}
