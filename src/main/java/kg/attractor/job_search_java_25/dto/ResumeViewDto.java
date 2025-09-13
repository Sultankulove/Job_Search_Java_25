package kg.attractor.job_search_java_25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeViewDto {
    private Long id;
    private Long applicantId;
    private Long categoryId;
    private String categoryName;
    private String name;
    private Float salary;
    private boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
