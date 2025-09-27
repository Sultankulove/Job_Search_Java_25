package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.ResponseDto;

import java.util.List;

public interface RespondService {
    boolean userInResponse(Long userId, Long responseId);

    boolean alreadyResponded(Long applicantId, Long vacancyId);
    Long respond(Long applicantId, Long vacancyId, Long resumeId);
    List<ResponseDto> listResponsesForVacancy(Long vacancyId);

    List<ResponseDto> listResponsesForApplicant(Long userId);
    List<ResponseDto> listResponsesForEmployer(Long userId);
}
