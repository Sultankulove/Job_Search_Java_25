package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import kg.attractor.job_search_java_25.exceptions.types.ConflictException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.repository.UserRepository;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void registration(RegistrationRequestDto rrd) {
        if (userRepository.existsByEmail(rrd.getEmail()))  throw new ConflictException("email");
        if (userRepository.existsByPhoneNumber(rrd.getPhoneNumber())) throw new ConflictException("phoneNumber");

        User user = new User();
        user.setName(rrd.getName());
        user.setSurname(rrd.getSurname());
        user.setAge(rrd.getAge());
        user.setEmail(rrd.getEmail());
        user.setPassword(passwordEncoder.encode(rrd.getPassword()));
        user.setPhoneNumber(rrd.getPhoneNumber());
        user.setAvatar(rrd.getAvatar());
        user.setAccountType(String.valueOf(rrd.getAccountType()));
        userRepository.save(user);
        log.info("Пользователь зарегистрирован email={}", rrd.getEmail());
    }


    @Override
    public Long findUserIdByEmail(String email) {
        return userRepository.findUserIdByEmail(email)
                .orElseThrow(() -> new NotFoundException("User email=" + email));
    }
}
