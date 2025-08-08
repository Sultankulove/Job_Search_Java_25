package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String name;

    private String surname;

    private Byte age;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;

    private String avatar;

    @NotNull
    private AccountType accountType;
}
