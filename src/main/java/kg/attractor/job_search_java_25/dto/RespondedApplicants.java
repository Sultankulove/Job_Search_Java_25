package kg.attractor.job_search_java_25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespondedApplicants {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private Boolean confirmation;
}
