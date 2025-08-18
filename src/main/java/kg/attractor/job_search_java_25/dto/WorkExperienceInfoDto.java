package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceInfoDto {
    @NotBlank(message = "Чей опыт работы")
    private Long resumeId;

    private Integer years;

    private String companyName;

    private String position;

    private String responsibilities;

}
