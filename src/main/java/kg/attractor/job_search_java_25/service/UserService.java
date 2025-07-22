package kg.attractor.job_search_java_25.service;


import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.dto.UserDto;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface UserService {


    void createUser();

    void editUser(Long id, UserDto userDto);

    void updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
