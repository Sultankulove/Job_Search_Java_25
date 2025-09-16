package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private Long id;

    @NotNull
    @Positive
    private Long resumeId;

    @NotNull
    @Positive
    private Long vacancyId;

    private String applicantName;
    private String resumeName;
    private String vacancyName;

    private Boolean confirmation;
    private LocalDateTime createdDate;
}
