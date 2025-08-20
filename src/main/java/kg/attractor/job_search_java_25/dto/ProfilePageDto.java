package kg.attractor.job_search_java_25.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePageDto {
    private Long id;
    private String name;
    private String surname;
    private Byte age;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String accountType;

    public boolean isEmployer() {
        return "EMPLOYER".equalsIgnoreCase(accountType);
    }
}