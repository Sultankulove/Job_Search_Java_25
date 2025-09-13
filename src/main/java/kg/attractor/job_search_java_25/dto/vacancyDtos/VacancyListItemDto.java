package kg.attractor.job_search_java_25.dto.vacancyDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyListItemDto {
    private Long id;
    private String name;
    private String categoryName;
    private Float salary;
    private Boolean active;
    private LocalDateTime updateTime;
}
