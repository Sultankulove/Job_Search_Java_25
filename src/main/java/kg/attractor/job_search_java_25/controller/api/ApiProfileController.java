package kg.attractor.job_search_java_25.controller.api;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/profile")
public class ApiProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping("resumes")
    public ResponseEntity<List<ResumeShortDto>> myResumes(Authentication authentication) {
        Long userId = userService.findUserIdByEmail(authentication.getName());
        var list = profileService.getMyShortResumes(userId);

        return ResponseEntity.ok(list);
    }

    @GetMapping("vacancies")
    public ResponseEntity<List<VacancyShortDto>> myVacancies(Authentication authentication) {
        Long userId = userService.findUserIdByEmail(authentication.getName());
        var list = profileService.getMyShortVacancies(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<MyProfileDto> getMyProfile(Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.debug("GET /api/profile/{} — получить профиль", authentication.getName());
        return profileService.getMyProfile(authId);
    }

    @PutMapping
    public ResponseEntity<MyProfileDto> editProfile(@RequestBody @Valid EditProfileDto epd, Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.info("PUT /api/profile — редактирование профиля userId={}", authId);

        MyProfileDto update = profileService.editProfile(epd, authId);
        return ResponseEntity.ok(update);
    }

    @PostMapping("avatar")
    public HttpStatus uploadAvatar(@RequestPart("avatar")MultipartFile avatar, Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        AvatarDto avatarDto = new AvatarDto();
        avatarDto.setUserId(authId);
        avatarDto.setAvatar(avatar);

        log.info("POST /api/profile/avatar — загрузка аватара пользователем");
        profileService.addAvatar(avatarDto);
        return HttpStatus.CREATED;
    }

    @GetMapping("avatar")
    public ResponseEntity<?> getAvatar(Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.debug("GET /api/profile/avatar?userId={} — получение аватара", authId);
        return profileService.getAvatarByUserId(authId);
    }


    // Получить свои отклики (для соискателя)
    @GetMapping("responses")
    public ResponseEntity<List<RespondedApplicantDto>> getMyResponses(Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.debug("GET /api/profile/responses — список моих откликов, userId={}", authId);
        return profileService.getMyResponses(authId);
    }

    // Получить отклики на мои вакансии (для работодателя)
    @GetMapping("/vacancy-responses")
    public ResponseEntity<List<RespondedApplicantDto>> getVacancyResponses(Authentication authentication) {
        Long employerId = userService.findUserIdByEmail(authentication.getName());
        // Вернуть список откликов на вакансии, созданные этим работодателем

        log.debug("GET /api/profile/vacancy-responses — отклики на мои вакансии, employerId={}", employerId);
        return profileService.getMyVacanciesResponses(employerId);
    }
}
