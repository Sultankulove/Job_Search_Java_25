package kg.attractor.job_search_java_25.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.job_search_java_25.dto.userDtos.RegistrationRequestDto;
import kg.attractor.job_search_java_25.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.UnsupportedEncodingException;

public interface UserService extends UserDetailsService {
    Long getUserIdOrNull();

    String getCurrentRole();

    boolean hasRole(String role);

    Long getRequiredUserId();

    void makeResetPasswordLink(HttpServletRequest request) throws UsernameNotFoundException, MessagingException, UnsupportedEncodingException;

    void updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    void registration(RegistrationRequestDto rrd);

    Long findUserIdByEmail(String name);

}
