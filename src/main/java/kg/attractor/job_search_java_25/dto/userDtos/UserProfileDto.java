package kg.attractor.job_search_java_25.dto.userDtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String surname;

    @Min(18)
    @Max(100)
    private Byte age;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp="^\\+?[0-9\\- ()]{7,20}$", message = "Неверный формат номера телефона")
    private String phoneNumber;
    private String avatar;

    private AccountType accountType;
}
