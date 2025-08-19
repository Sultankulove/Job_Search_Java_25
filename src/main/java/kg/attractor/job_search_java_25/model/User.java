package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "surname", length = 64)
    private String surname;

    @Column(name = "age")
    private Byte age;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 55, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "account_type", length = 50, nullable = false)
    private String accountType;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Resume> resumes;
}
