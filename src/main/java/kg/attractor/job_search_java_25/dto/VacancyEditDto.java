package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyEditDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    private Long categoryId;

    @PositiveOrZero
    private Float salary;

    @PositiveOrZero
    private Integer expFrom;

    @PositiveOrZero
    private Integer expTo;

    private boolean active;
}
