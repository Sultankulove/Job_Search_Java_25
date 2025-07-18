package kg.attractor.job_search_java_25.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResumeDto {
    private Long id;
    private Long applicantId;
    private String name;
    private Long categoryId;
    private Double salary;
    private Boolean isActive;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
