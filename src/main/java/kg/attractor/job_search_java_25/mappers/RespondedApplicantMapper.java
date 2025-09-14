package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RespondedApplicantMapper {
    public RespondedApplicantDto toDto(RespondedApplicant r) {
        if (r == null) return null;
        return RespondedApplicantDto.builder()
                .id(r.getId())
                .resumeId(r.getResume()  != null ? r.getResume().getId()  : null)
                .vacancyId(r.getVacancy() != null ? r.getVacancy().getId() : null)
                .confirmation(r.getConfirmation())
                .build();
    }

    public RespondedApplicant toEntity(Resume resume, Vacancy vacancy, boolean confirmation) {
        var e = new RespondedApplicant();
        e.setResume(resume);
        e.setVacancy(vacancy);
        e.setConfirmation(confirmation);
        return e;
    }

    public void applyUpsert(RespondedApplicant e, Resume resume, Vacancy vacancy, Boolean confirmation) {
        if (resume  != null) e.setResume(resume);
        if (vacancy != null) e.setVacancy(vacancy);
        if (confirmation != null) e.setConfirmation(confirmation);
    }

    public List<RespondedApplicantDto> toDtoList(List<RespondedApplicant> list) {
        return list == null ? List.of() : list.stream().map(this::toDto).toList();
    }
}
