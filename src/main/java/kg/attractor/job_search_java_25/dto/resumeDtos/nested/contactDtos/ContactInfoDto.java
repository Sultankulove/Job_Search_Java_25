package kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfoDto {
    @NotNull(message = "Выберите тип контакта")
    private Long typeId;


    @NotBlank(message = "Заполните значение контакта")
    @Size(max = 255)
    private String contactValue;

}
