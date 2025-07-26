package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("profile")
public class ProfileController {
    private final ImageService imageService;
    private final UserDao userDao;

    @GetMapping("avatar")
    public ResponseEntity<?> getImage(@RequestParam(name = "filename") String filename) {
        return imageService.getImageById(filename, "avatar");
//        http://localhost:8888/profile/avatar?filename=838a1381-9aed-4dec-80f1-1663ca3f3863_Animals___Wild_cats_Moody_tiger_076162_.jpeg
//        для вызова аватарки,
//        при сохранении аватарка перезаписывает старую
    }

    @PostMapping("avatar")
    public ResponseEntity<?> addImage(AvatarDto avatarDto) {
        imageService.addImage(avatarDto);
//        Примечание: может сохранить любой файл, надо исправить
        return ResponseEntity.ok("Аватар успешно загружен");
    }
}
