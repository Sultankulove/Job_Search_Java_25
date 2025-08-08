package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("{auth}")
    public ResponseEntity<MyProfileDto> getMyProfile(@PathVariable Long auth) {
        // Вернуть информацию о пользователе (по JWT или сессии)
//        Long auth = 1L;
        return profileService.getMyProfile(auth);
    }

    @PutMapping
    public ResponseEntity<Void> editProfile(@RequestBody EditProfileDto epd) {
        // Логика обновления профиля
        Long auth = 1L;
        return profileService.editProfile(epd, auth);
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
    public ResponseEntity<List<RespondedApplicantDto>> getMyResponses() {
        // Вернуть список откликов пользователя

        Long auth = 1L;
        return profileService.getMyResponses(auth);
    }

    // Получить отклики на мои вакансии (для работодателя)
    @GetMapping("/vacancy-responses")
    public ResponseEntity<List<RespondedApplicantDto>> getVacancyResponses() {
        // Вернуть список откликов на вакансии, созданные этим работодателем
        Long auth = 1L;
        return profileService.getMyVacanciesResponses(auth);
    }
}
