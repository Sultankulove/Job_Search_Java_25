package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondRequestDto {
    @NotNull(message = "ID резюме обязателен")
    Long resumeId;
}
