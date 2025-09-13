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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ApplicationConfig applicationConfig;
    private final UserRepository userRepository;
    private final EmailService emailService;

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
