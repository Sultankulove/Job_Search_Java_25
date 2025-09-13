package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.ResponseDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyUpsertDto;
import kg.attractor.job_search_java_25.model.*;
import org.springframework.stereotype.Component;

@Component
public class RespondedApplicantMapper {
    public ResponseDto toView(RespondedApplicant ra) {
        if (ra == null) return null;
        return new ResponseDto(
                ra.getResume().getId(),
                ra.getVacancy().getId()
        );
    }

    public RespondedApplicant applyUpsert(RespondedApplicant e, Resume resume, Vacancy vacancy, boolean confirmation) {
        e.setResume(resume);
        e.setVacancy(vacancy);
        e.setConfirmation(confirmation);
        return e;
    }
}
