package kg.attractor.job_search_java_25.service.impl;

import jakarta.validation.constraints.*;
import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.UserDto;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType());

        userDao.save(user);
        return user;
    }

    @Override
    public void editUser(Long id, UserDto userDto) {

    }

    @Override
    public void updateUser(Long id, UserDto userDto) {

    }

    @Override
    public void deleteUser(Long id) {

    }
}
