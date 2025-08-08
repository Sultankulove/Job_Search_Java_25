package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.RegistrationRequestDto;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    @Override
    public void registration(RegistrationRequestDto rrd) {
        User user = new User();
        // Регистрация: Имя! Email! NumberPhone! Avatar(if not default)
        user.setName(rrd.getName());
        user.setSurname(rrd.getSurname());
        user.setAge(rrd.getAge());
        user.setEmail(rrd.getEmail());
        user.setPassword(rrd.getPassword());
        user.setPhoneNumber(rrd.getPhoneNumber());
        user.setAvatar(rrd.getAvatar());
        user.setAccountType(String.valueOf(rrd.getAccountType()));

        userDao.createUser(user);
    }
}
