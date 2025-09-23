package kg.attractor.job_search_java_25.service.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.job_search_java_25.config.ApplicationConfig;
import kg.attractor.job_search_java_25.dto.userDtos.RegistrationRequestDto;
import kg.attractor.job_search_java_25.exceptions.types.ConflictException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.exceptions.types.UserNotFoundException;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.repository.UserRepository;
import kg.attractor.job_search_java_25.service.UserService;
import kg.attractor.job_search_java_25.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ApplicationConfig applicationConfig;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public Long getUserIdOrNull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User springUser) {
            String email = springUser.getUsername();
            return userRepository.findUserIdByEmailIgnoreCase(email).orElse(null);
        }
        return null;
    }

    @Override
    public String getCurrentRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }


    @Override
    public Long getRequiredUserId() {
        return Optional.ofNullable(getUserIdOrNull())
                .orElseThrow(() -> new IllegalStateException("Пользователь не аутентифицирован"));
    }

    @Override
    public Long getRequiredUserId(Principal principal) {
        String email = Optional.ofNullable(principal)
                .map(Principal::getName)
                .orElseThrow(() -> new IllegalStateException("Пользователь не аутентифицирован"));
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден: " + email));
    }



    @Override
    public void makeResetPasswordLink(HttpServletRequest request) throws UserNotFoundException, MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        updateResetPasswordToken(token, email);

        String url = Utility.makeSiteUrl(request) + "/auth/reset-password?token=" + token;
        emailService.sendEmail(email, url);
    }


    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = applicationConfig.passwordEncoder().encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void registration(RegistrationRequestDto rrd) {
        if (userRepository.existsByEmail(rrd.getEmail()))  throw new ConflictException("email");
        if (userRepository.existsByPhoneNumber(rrd.getPhoneNumber())) throw new ConflictException("phoneNumber");

        User user = new User();
        user.setName(rrd.getName());
        user.setSurname(rrd.getSurname());
        user.setAge(rrd.getAge());
        user.setEmail(rrd.getEmail());
        user.setPassword(applicationConfig.passwordEncoder().encode(rrd.getPassword()));
        user.setPhoneNumber(rrd.getPhoneNumber());
        user.setAvatar(rrd.getAvatar());
        user.setAccountType(String.valueOf(rrd.getAccountType()));
        user.setEnabled(true);
        userRepository.save(user);
    }


    @Override
    public Long findUserIdByEmail(String email) {
        return userRepository.findUserIdByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User email=" + email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is null or blank");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is null or blank");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

}
