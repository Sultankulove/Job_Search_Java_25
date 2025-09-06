package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEditDto {
    @NotBlank
    @Size(max = 64)
    private String name;

    @NotNull
    @Positive
    private Long categoryId;

    @PositiveOrZero
    private Float salary;

    private boolean active;

    private List<WorkExperienceInfoDto> workExperiences = new ArrayList<>();
    private List<EducationInfoDto> educationInfos = new ArrayList<>();
    private List<ContactInfoDto> contactInfos = new ArrayList<>();
}
