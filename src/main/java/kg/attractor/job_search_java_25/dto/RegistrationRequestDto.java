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

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String surname;

    @Min(18) @Max(100)
    private Byte age;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Пароль должен содержать хотя бы 1 заглавную букву и 1 цифру")
    private String password;

    @NotBlank
    @Pattern(regexp="^\\+?[0-9\\- ()]{7,20}$", message = "Неверный формат номера телефона")
    private String phoneNumber;

    private String avatar;

    @NotNull
    private AccountType accountType;
}
