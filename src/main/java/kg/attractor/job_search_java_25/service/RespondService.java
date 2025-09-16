package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ResponseDto;

import java.util.List;

public interface RespondService {
    boolean alreadyResponded(Long applicantId, Long vacancyId);
    void respond(Long applicantId, Long vacancyId);
    List<ResponseDto> listResponsesForVacancy(Long vacancyId);

    List<ResponseDto> listResponsesForApplicant(Long userId);
}
