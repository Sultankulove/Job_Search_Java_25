package kg.attractor.job_search_java_25.dto.responseDto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespondedApplicantUpsertDto {
    @NotNull
    private Long resumeId;

    @NotNull
    private Long vacancyId;

    private Boolean confirmation;
}
