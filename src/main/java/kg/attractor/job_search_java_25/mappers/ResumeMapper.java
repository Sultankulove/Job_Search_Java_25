package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.dto.ResumeListViewDto;
import kg.attractor.job_search_java_25.dto.ResumeShortDto;
import kg.attractor.job_search_java_25.model.Resume;

import java.util.List;

public class ResumeMapper {

    public static ResumeDto toDto(Resume resume) {
        if (resume == null) return null;

        ResumeDto dto = new ResumeDto();

        dto.setApplicantId(resume.getApplicant().getId());
        dto.setName(resume.getName());
        dto.setCategoryId(resume.getCategory().getId());
        dto.setSalary(resume.getSalary());
        dto.setIsActive(resume.getIsActive());
        dto.setCreatedDate(resume.getCreatedDate());
        dto.setUpdateTime(resume.getUpdateTime());
        return dto;
    }

    public static List<ResumeDto> toDtoList(List<Resume> resumes) {
        return resumes.stream()
                .map(ResumeMapper::toDto)
                .toList();
    }

    public static ResumeShortDto toShortDto(Resume resume) {
        if (resume == null) return null;
        ResumeShortDto dto = new ResumeShortDto();

        dto.setName(resume.getName());
        dto.setUpdateTime(resume.getUpdateTime());
        return dto;
    }

    public static ResumeListViewDto toListViewDto(Resume resume) {
        if (resume == null) return null;
        ResumeListViewDto dto = new ResumeListViewDto();
        dto.setApplicantId(resume.getApplicant().getId());
        dto.setCategoryId(resume.getCategory().getId());
        dto.setCategoryName(resume.getCategory().getName());
        dto.setName(resume.getName());
        dto.setSalary(resume.getSalary());
        dto.setIsActive(resume.getIsActive());
        dto.setCreatedDate(resume.getCreatedDate());
        dto.setUpdateTime(resume.getUpdateTime());
        return dto;
    }
    public static List<ResumeListViewDto> toListViewDtoList(List<Resume> resumes) {
        return resumes.stream()
                .map(ResumeMapper::toListViewDto)
                .toList();
    }
}
