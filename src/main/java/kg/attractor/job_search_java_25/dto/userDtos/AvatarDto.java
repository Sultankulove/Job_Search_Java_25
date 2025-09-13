package kg.attractor.job_search_java_25.dto.userDtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvatarDto {
    @NotNull(message = "Файл обязателен")
    private MultipartFile avatar;

    private Long userId;
}
