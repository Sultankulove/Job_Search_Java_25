package kg.attractor.job_search_java_25.dto.vacancyDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacancySearchDto {
    private Long categoryId;
    private Float minSalary;
    private Float maxSalary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean activeOnly;
    private String term;
    private String sort;
    private Integer page;
    private Integer size;
}
