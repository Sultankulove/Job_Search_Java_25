package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.dto.UserDto;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.service.ImageService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("profile")
public class ProfileController {
    private final ImageService imageService;
    private final UserService userService;

    private final UserDao userDao;

//    // Получить текущий профиль
    @GetMapping
    public UserDto getProfile(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    // Редактировать профиль
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public User editUser(Principal principal, @RequestBody UserDto userDto) {
        return userService.editUser(principal.getName(), userDto);
    }

    // Скачать аватар по filename
    @GetMapping("avatar")
    public ResponseEntity<?> getImage(@RequestParam(name = "filename") String filename) {
        return imageService.getImageById(filename, "avatar");
//        при сохранении аватарка перезаписывает старую
    }

    // Загрузить/обновить аватар
    @PostMapping("avatar")
    public ResponseEntity<?> addImage(AvatarDto avatarDto) {
        imageService.addImage(avatarDto);
//        Примечание: может сохранить любой файл, надо исправить
        return ResponseEntity.ok("Аватар успешно загружен");
    }
}
