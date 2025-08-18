package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;

    @NotNull(message = "ID отклика обязателен")
    @Positive(message = "ID отклика должен быть положительным")
    private Long respondedApplicants;

    @NotBlank(message = "Сообщение не может быть пустым")
    @Size(max = 1000, message = "Сообщение не должно превышать 1000 символов")
    private String content;

    private Timestamp timestamp;
}
