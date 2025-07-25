package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
    private String surname;

    @NotNull(message = "Возраст обязателен")
    @Min(value = 16, message = "Возраст должен быть не менее 16 лет")
    @Max(value = 128, message = "Возраст не должен превышать 128 лет")
    private Short age;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email")
    private String email;

    @NotBlank
    @Size(min = 3, max = 24, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-z]).+$"
            , message = "Должно содержать хотя бы одну заглавную букву и одну цифру")
    private String password;


    @NotBlank(message = "Номер телефона обязателен")
    private String phoneNumber;

    private String avatar;

    @NotBlank(message = "Кто вы? Укажите: Соискатель либо Работодатель")
    private String accountType;
}
