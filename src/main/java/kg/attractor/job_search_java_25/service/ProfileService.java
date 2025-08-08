package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfileService {
    void addAvatar(AvatarDto avatarDto);

    ResponseEntity<?> getAvatarByUserId(Long userId);

    ResponseEntity<MyProfileDto> getMyProfile(Long auth);

    ResponseEntity<Void> editProfile(EditProfileDto epd, Long auth);

    ResponseEntity<List<RespondedApplicantDto>> getMyResponses(Long auth);

    ResponseEntity<List<RespondedApplicantDto>> getMyVacanciesResponses(Long auth);
}
