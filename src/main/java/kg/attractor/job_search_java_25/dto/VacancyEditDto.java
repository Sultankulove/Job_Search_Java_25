package kg.attractor.job_search_java_25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyEditDto {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private Float salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean isActive;
}
