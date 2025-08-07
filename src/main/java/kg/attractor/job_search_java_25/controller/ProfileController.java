package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.dto.EditProfileDto;
import kg.attractor.job_search_java_25.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping
    public String getMyProfile() {
        // Вернуть информацию о пользователе (по JWT или сессии)
        return "OK";
    }

    @PutMapping
    public String editProfile(@RequestBody EditProfileDto epd) {
        // Логика обновления профиля
        return "OK";
    }

    @PostMapping("avatar")
    public HttpStatus uploadAvatar(AvatarDto avatarDto) {
        // Логика смены аватара
        profileService.addAvatar(avatarDto);
        return HttpStatus.CREATED;
    }

    @GetMapping("avatar")
    public ResponseEntity<?> getAvatar(@RequestParam(name = "userId") Long userId) {

        return profileService.getAvatarByUserId(userId);
    }


    // Получить свои отклики (для соискателя)
    @GetMapping("responses")
    public String getMyResponses() {
        // Вернуть список откликов пользователя
        return "OK";
    }

    // Получить отклики на мои вакансии (для работодателя)
    @GetMapping("/vacancy-responses")
    public String getVacancyResponses() {
        // Вернуть список откликов на вакансии, созданные этим работодателем
        return "OK";
    }
}
