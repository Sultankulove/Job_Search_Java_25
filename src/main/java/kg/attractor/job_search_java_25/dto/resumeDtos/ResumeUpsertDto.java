package kg.attractor.job_search_java_25.dto.resumeDtos;

import jakarta.validation.constraints.*;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUpsertDto {
    @NotBlank
    @Size(max = 64)
    private String name;

    @NotNull
    @Positive
    private Long categoryId;

    @PositiveOrZero
    private Float salary;

    @NotNull
    private Boolean active;

    private List<WorkExperienceInfoDto> workExperiences;
    private List<EducationInfoDto> educationInfos;
    private List<ContactInfoDto> contactInfos;
}
