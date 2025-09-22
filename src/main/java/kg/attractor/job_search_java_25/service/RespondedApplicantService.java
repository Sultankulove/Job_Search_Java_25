package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantUpsertDto;

import java.util.List;

public interface RespondedApplicantService {
    List<RespondedApplicantDto> getAll();
    RespondedApplicantDto getById(Long id);
    RespondedApplicantDto create(RespondedApplicantUpsertDto dto);
    RespondedApplicantDto update(Long id, RespondedApplicantUpsertDto dto);
    void delete(Long id);

    boolean alreadyResponded(Long resumeId, Long vacancyId);
    List<RespondedApplicantDto> listByVacancy(Long vacancyId);
    List<RespondedApplicantDto> listByResume(Long resumeId);
    long countForEmployer(Long employerUserId);
    long countForApplicant(Long applicantUserId);
}
