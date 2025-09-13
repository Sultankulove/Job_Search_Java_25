package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceInfoDto {

    @PositiveOrZero
    private Integer years;
    private String companyName;
    private String position;
    private String responsibilities;
}
