package kg.attractor.job_search_java_25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperienceInfoDto {
    private Long resumeId;
    private Long years;
    private String companyName;
    private String position;
    private String responsibilities;
}
