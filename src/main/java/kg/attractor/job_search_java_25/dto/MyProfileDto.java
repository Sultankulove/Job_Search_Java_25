package kg.attractor.job_search_java_25.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileDto {
    private String name;
    private String surname;
    private Byte age;
    private String email;
    private String phoneNumber;
    private String avatar;
}
