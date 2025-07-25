package kg.attractor.job_search_java_25.service;


import kg.attractor.job_search_java_25.dto.UserDto;
import kg.attractor.job_search_java_25.model.User;


public interface UserService {


    User createUser(UserDto userDto);

    void editUser(Long id, UserDto userDto);

    void updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
