package kg.attractor.job_search_java_25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationInfoDto {

    private String institution;
    private String program;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
}
