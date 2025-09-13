package kg.attractor.job_search_java_25.dto.vacancyDtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyUpsertDto {
    @NotBlank
    @Size(max=100)
    private String name;

    @NotBlank
    @Size(max=500)
    private String description;

    @NotNull
    @Positive
    private Long categoryId;

    @PositiveOrZero
    private Float salary;

    @PositiveOrZero
    private Integer expFrom;

    @PositiveOrZero
    private Integer expTo;

    @NotNull
    private Boolean active;
}
