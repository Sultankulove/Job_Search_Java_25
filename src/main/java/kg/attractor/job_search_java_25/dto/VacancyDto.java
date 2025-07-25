package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacancyDto {
    private Long id;

    @NotBlank(message = "Название вакансии обязательно")
    private String name;

    @NotBlank(message = "Описание вакансии обязательно")
    private String description;

    @NotNull(message = "Категория обязательна")
    private Long categoryId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Зарплата должна быть положительной")
    private Double salary;

    @Min(value = 0)
    private Long expFrom;

    @Min(value = 0)
    private Long expTo;

    private Boolean isActive;

    @NotNull(message = "Автор вакансии обязателен")
    private Long authorId;

    private LocalDateTime createdDate;

    private LocalDateTime updateTime;
}

