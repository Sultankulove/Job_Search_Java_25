package kg.attractor.job_search_java_25.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

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

    private String resetPasswordToken;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Resume> resumes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = switch (accountType) {
            case "ADMIN" -> "ROLE_ADMIN";
            case "EMPLOYER" -> "ROLE_EMPLOYER";
            case "APPLICANT" -> "ROLE_APPLICANT";
            default -> "ROLE_APPLICANT";
        };
        return List.of(new SimpleGrantedAuthority(role));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
