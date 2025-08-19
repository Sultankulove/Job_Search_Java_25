package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDto {

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String surname;

    @Min(18) @Max(100)
    private Byte age;

    @Email
    @Size(max = 64)
    private String email;

    @Size(min = 8, max = 64)
    private String password;

    @Pattern(regexp="^\\+?[0-9\\- ()]{7,20}$")
    private String phoneNumber;

    private String avatar;

    public EditProfileDto(@NotBlank @Size(max = 50) String name, @Size(max = 50) String surname, @Min(18) @Max(100) Byte age, @NotBlank @Email String email, @NotBlank @Pattern(regexp="^\\+?[0-9\\- ()]{7,20}$", message = "Неверный формат номера телефона") String phoneNumber) {

    }
}
