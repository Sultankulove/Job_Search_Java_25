package kg.attractor.job_search_java_25.service;

public interface ResponseService {

    long countForEmployer(Long employerId);

    long countForApplicant(Long applicantId);
}
