package kg.attractor.job_search_java_25.dto.userDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserViewProfileDto {
    private Long id;
    private String name;
    private String username;
    private Byte age;
    private String email;
    private String phone;
    private String avatar;
    private AccountType accountType;
}
