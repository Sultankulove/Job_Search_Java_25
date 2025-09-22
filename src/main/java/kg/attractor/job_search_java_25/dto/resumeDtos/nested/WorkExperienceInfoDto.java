package kg.attractor.job_search_java_25.dto.resumeDtos.nested;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    private String companyName;

    @Size(max=64)
    private String position;

    @Size(max=2000)
    private String responsibilities;
}
