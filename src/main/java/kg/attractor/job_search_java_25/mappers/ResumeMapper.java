package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.ActiveDto;
import kg.attractor.job_search_java_25.dto.contactDros.ContactInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeViewDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import kg.attractor.job_search_java_25.model.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class ResumeMapper {

    public ResumeViewDto toView(Resume r) {
        if (r == null) return null;
        return new ResumeViewDto(
                r.getId(),
                r.getApplicant() != null ? r.getApplicant().getId() : null,
                r.getCategory()  != null ? r.getCategory().getId()  : null,
                r.getCategory()  != null ? r.getCategory().getName(): null,
                r.getName(),
                r.getSalary(),
                r.getIsActive(),
                r.getCreatedDate(),
                r.getUpdateTime()
        );
    }

    public ResumeListItemDto toListItem(Resume r) {
        if (r == null) return null;
        return new ResumeListItemDto(
                r.getId(),
                r.getName(),
                r.getCategory() != null ? r.getCategory().getName() : null,
                r.getSalary(),
                r.getIsActive(),
                r.getUpdateTime()
        );
    }

    public void applyUpsert(ResumeUpsertDto d, Resume e, Category category, User applicant) {
        e.setName(d.getName());
        e.setSalary(d.getSalary());
        e.setActive(Boolean.TRUE.equals(d.getActive()));
        e.setCategory(category);
        if (applicant != null) e.setApplicant(applicant);

        if (d.getWorkExperiences() != null) {
            e.getWorkExperiences().clear();
            e.getWorkExperiences().addAll(d.getWorkExperiences().stream()
                    .map(this::toWorkExpEntity)
                    .peek(w -> w.setResume(e))
                    .toList());
        }
        if (d.getEducationInfos() != null) {
            e.getEducationInfos().clear();
            e.getEducationInfos().addAll(d.getEducationInfos().stream()
                    .map(this::toEducationEntity)
                    .peek(ed -> ed.setResume(e))
                    .toList());
        }
        if (d.getContactInfos() != null) {
            e.getContactInfos().clear();
            e.getContactInfos().addAll(d.getContactInfos().stream()
                    .map(ci -> toContactEntity(ci, null)) // тип подставим позже
                    .peek(ci -> ci.setResume(e))
                    .toList());
        }
    }

    public void applyActive(ActiveDto dto, Resume e) {
        if (dto != null && dto.getActive() != null) e.setActive(dto.getActive());
    }

    private WorkExperienceInfo toWorkExpEntity(WorkExperienceInfoDto d) {
        var e = new WorkExperienceInfo();
        e.setYears(d.getYears());
        e.setCompanyName(d.getCompanyName());
        e.setPosition(d.getPosition());
        e.setResponsibilities(d.getResponsibilities());
        return e;
    }

    private EducationInfo toEducationEntity(EducationInfoDto d) {
        var e = new EducationInfo();
        e.setInstitution(d.getInstitution());
        e.setProgram(d.getProgram());
        e.setDegree(d.getDegree());

        e.setStartDate(d.getStartDate() != null ? Date.valueOf(d.getStartDate()) : null);
        e.setEndDate  (d.getEndDate()   != null ? Date.valueOf(d.getEndDate())   : null);
        return e;
    }

    private ContactInfo toContactEntity(ContactInfoDto d, ContactType contactType) {
        var e = new ContactInfo();
        e.setType(contactType);
        e.setContactValue(d.getContactValue());
        return e;
    }

    public List<ResumeListItemDto> toListItems(List<Resume> list) {
        return list == null ? List.of() : list.stream().map(this::toListItem).toList();
    }
    public List<ResumeViewDto> toViews(List<Resume> list) {
        return list == null ? List.of() : list.stream().map(this::toView).toList();
    }
}
