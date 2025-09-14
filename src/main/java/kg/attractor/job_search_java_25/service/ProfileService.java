package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.userDtos.AvatarDto;
import kg.attractor.job_search_java_25.dto.userDtos.EditProfileDto;
import kg.attractor.job_search_java_25.dto.userDtos.UserProfileDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileService {

    @Transactional
    void addAvatar(AvatarDto avatarDto);

    ResponseEntity<?> findAvatarById(Long userId);

    UserProfileDto getMyProfile(Long id);

    @Transactional
    UserProfileDto updateProfileByUserId(EditProfileDto dto, Long id);

    List<RespondedApplicantDto> getMyResponses(Long authUserId);

    List<RespondedApplicantDto> getMyVacanciesResponses(Long authUserId, Pageable p);

    List<VacancyListItemDto> getMyShortVacancies(Long userId);

    List<ResumeListItemDto> getMyShortResumes(Long userId);

}
