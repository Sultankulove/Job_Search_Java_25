package kg.attractor.job_search_java_25.dto.resumeDtos.nested;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    @NotNull
    @Positive
    private Long resumeId;

    @NotNull
    @Positive
    private Long vacancyId;
}
