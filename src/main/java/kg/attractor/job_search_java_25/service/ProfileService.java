package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfileService {
    void addAvatar(AvatarDto avatarDto);

    ResponseEntity<?> findAvatarById(Long userId);

    UserProfileDto getMyProfile(Long auth);

    UserProfileDto updateProfileByUserId(EditProfileDto epd, Long auth);

    ResponseEntity<List<RespondedApplicantDto>> getMyResponses(Long auth);

    ResponseEntity<List<RespondedApplicantDto>> getMyVacanciesResponses(Long auth, Pageable p);

    List<VacancyShortDto> getMyShortVacancies(Long userId);

    List<ResumeShortDto> getMyShortResumes(Long userId);

    UserProfileDto getUserProfile(Long id);
}
