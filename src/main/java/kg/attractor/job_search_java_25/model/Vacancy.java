package kg.attractor.job_search_java_25.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Vacancy {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private Double salary;
    private Long expFrom;
    private Long expTo;
    private Boolean isActive;
    private Long authorId;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
