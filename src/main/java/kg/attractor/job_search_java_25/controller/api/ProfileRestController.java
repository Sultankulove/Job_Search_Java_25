package kg.attractor.job_search_java_25.controller.api;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.userDtos.AvatarDto;
import kg.attractor.job_search_java_25.dto.userDtos.EditProfileDto;
import kg.attractor.job_search_java_25.dto.userDtos.UserProfileDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/profile")
public class ProfileRestController {
    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping("resumes")
    public ResponseEntity<List<ResumeListItemDto>> myResumes(Authentication authentication) {
        Long userId = userService.findUserIdByEmail(authentication.getName());
        var list = profileService.getMyShortResumes(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("vacancies")
    public ResponseEntity<List<VacancyListItemDto>> myVacancies(Authentication authentication) {
        Long userId = userService.findUserIdByEmail(authentication.getName());
        var list = profileService.getMyShortVacancies(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public UserProfileDto getMyProfile(Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.debug("GET /api/profile/{} возвращен профиль", authentication.getName());
        return profileService.getMyProfile(authId);
    }

    @PutMapping
    public ResponseEntity<UserProfileDto> editProfile(@RequestBody @Valid EditProfileDto epd,
                                                      Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.info("PUT /api/profile редактирован профиль userId={}", authId);
        return ResponseEntity.ok(profileService.updateProfileByUserId(epd, authId));
    }

    @PostMapping("avatar")
    public ResponseEntity<Void> uploadAvatar(@RequestPart("avatar") MultipartFile avatar,
                                             Principal principal) {
        Long authId = userService.findUserIdByEmail(principal.getName());
        profileService.addAvatar(AvatarDto.builder().userId(authId).avatar(avatar).build());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("avatar")
    public ResponseEntity<?> getAvatar(Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.debug("GET /api/profile/avatar  аватар userId={}", authId);
        return profileService.findAvatarById(authId);
    }

    @GetMapping("/avatar/{id}")
    public ResponseEntity<?> getAvatarById(Authentication authentication,
                                           @PathVariable Long id) {
        log.debug("GET /api/profile/avatar  аватар userId={}", id);
        return profileService.findAvatarById(id);
    }

    @GetMapping("responses")
    public ResponseEntity<List<RespondedApplicantDto>> getMyResponses(Authentication authentication) {
        Long authId = userService.findUserIdByEmail(authentication.getName());
        log.debug("GET /api/profile/responses  userId={}", authId);
        return ResponseEntity.ok(profileService.getMyResponses(authId));
    }

    @GetMapping("vacancy-responses")
    public ResponseEntity<List<RespondedApplicantDto>> getVacancyResponses(Authentication authentication,
                                                                           Pageable p) {
        Long employerId = userService.findUserIdByEmail(authentication.getName());
        log.debug("GET /api/profile/vacancy-responses  employerId={}", employerId);
        return ResponseEntity.ok(profileService.getMyVacanciesResponses(employerId, p));
    }
}