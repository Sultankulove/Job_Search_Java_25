package kg.attractor.job_search_java_25.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResumeListDto {
    private String name;
    private LocalDateTime updateTime;
}
