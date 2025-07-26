package kg.attractor.job_search_java_25.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class AvatarDto {
    private MultipartFile file;
    private Long userId;
}
