package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequestDto {

    @NotBlank(message = "Заполни имя!")
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String surname;

    @Min(18) @Max(100)
    private Byte age;

    @NotBlank(message = "Email обязателен!")
    @Email
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 8, max = 64)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Пароль должен содержать хотя бы 1 заглавную букву и 1 цифру")
    private String password;

    @NotBlank(message = "Номер телефона обязателен!")
    @Pattern(regexp="^\\+?[0-9\\- ()]{7,20}$", message = "Неверный формат номера телефона")
    private String phoneNumber;

    private String avatar;

//    @NotNull(message = "Выбери тип аккаунта")
    private AccountType accountType;
}
