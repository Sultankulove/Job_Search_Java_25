package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void registration(RegistrationRequestDto rrd);

    Long findUserIdByEmail(String name);
}
