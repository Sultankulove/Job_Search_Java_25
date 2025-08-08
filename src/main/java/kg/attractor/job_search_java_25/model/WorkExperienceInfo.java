package kg.attractor.job_search_java_25.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkExperienceInfo {
    private Long id;
    private Long resumeId;
    private Integer years;
    private String companyName;
    private String position;
    private String responsibilities;
}
