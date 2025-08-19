package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeEditDto {
    @NotBlank
    @Size(max = 64)
    private String name;

    @NotNull
    @Positive
    private Long categoryId;

    @PositiveOrZero
    private Float salary;

    private boolean isActive;

    public boolean getActive() {
        return isActive;
    }
}
