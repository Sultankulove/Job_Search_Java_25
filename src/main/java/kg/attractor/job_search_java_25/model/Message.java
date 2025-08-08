package kg.attractor.job_search_java_25.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Message {
    private Long id;
    private Long respondedApplicants;
    private String content;
    private Timestamp timestamp;
}
