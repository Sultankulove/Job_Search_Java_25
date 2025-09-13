package kg.attractor.job_search_java_25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancyViewDto {
    private Long id;
    private Long authorId;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String description;
    private Float salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
};