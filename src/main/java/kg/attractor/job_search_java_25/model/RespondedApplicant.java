package kg.attractor.job_search_java_25.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespondedApplicant {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private Boolean confirmation;
}
