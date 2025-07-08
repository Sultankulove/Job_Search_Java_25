package kg.attractor.job_search_java_25.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String surname;
    private Short age;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatar;
    private String accountType;
}
