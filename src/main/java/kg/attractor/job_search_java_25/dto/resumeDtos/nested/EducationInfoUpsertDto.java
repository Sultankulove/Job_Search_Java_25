package kg.attractor.job_search_java_25.dto.resumeDtos.nested;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationInfoUpsertDto {

    private Long resume;

    @NotBlank
    private String institution;

    @Size(max = 128)
    private String program;

    @Size(max = 64)
    private String degree;

    private LocalDate startDate;

    private LocalDate endDate;
}
