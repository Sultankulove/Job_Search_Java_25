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


    private Long id;

    private Long resume_id;

    private String institution;

    private String program;

    private LocalDate startDate;

    private LocalDate endDate;

    private String degree;

}
