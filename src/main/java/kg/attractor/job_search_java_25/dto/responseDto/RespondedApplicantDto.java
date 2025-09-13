package kg.attractor.job_search_java_25.dto.responseDto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondedApplicantDto {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private Boolean confirmation;
}
