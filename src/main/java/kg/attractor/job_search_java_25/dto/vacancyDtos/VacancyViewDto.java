package kg.attractor.job_search_java_25.dto.vacancyDtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean active;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateTime;
};