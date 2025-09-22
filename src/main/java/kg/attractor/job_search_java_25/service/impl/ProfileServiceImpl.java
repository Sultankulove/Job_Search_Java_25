package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.ResumeListItemDto;
import kg.attractor.job_search_java_25.dto.userDtos.AvatarDto;
import kg.attractor.job_search_java_25.dto.userDtos.EditProfileDto;
import kg.attractor.job_search_java_25.dto.userDtos.UserProfileDto;
import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.mappers.UserMapper;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.repository.RespondedApplicantRepository;
import kg.attractor.job_search_java_25.repository.ResumeRepository;
import kg.attractor.job_search_java_25.repository.UserRepository;
import kg.attractor.job_search_java_25.repository.VacancyRepository;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final UserMapper userMapper;

    private static final String SUB_DIR = "avatar";

    private static final int SHORT_LIMIT = 10;

    @Transactional
    @Override
    public void addAvatar(AvatarDto avatarDto) {
        log.info("Профиль: загрузка аватара userId={}", avatarDto.getUserId());
        User user = userRepository.findById(avatarDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User id=" + avatarDto.getUserId()));
        String avatar = FileUtil.saveUploadedFile(avatarDto.getAvatar(), SUB_DIR);
        int updated = userRepository.saveAvatar(avatar, user.getId());
        if (updated == 0) throw new NotFoundException("User id=" + user.getId());
        log.debug("Аватар сохранён: {}", avatar);
    }

    @Override
    public ResponseEntity<?> findAvatarById(Long userId) {
        log.debug("Профиль: получение аватара userId={}", userId);
        String avatar = userRepository.findAvatarPathById(userId)
                .orElseThrow(() -> new NotFoundException("Avatar for user id=" + userId));
        return FileUtil.downloadImage(avatar, SUB_DIR);
    }

    @Override
    public UserProfileDto getMyProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User id=" + id));
        return userMapper.toProfile(user);
    }

    @Transactional
    @Override
    public UserProfileDto updateProfileByUserId(EditProfileDto dto, Long id) {
        int updated = userRepository.editProfile(
                id, dto.getName(), dto.getSurname(), dto.getAge(), dto.getEmail(), dto.getPhoneNumber()
        );
        if (updated == 0) throw new NotFoundException("User id=" + id);
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id=" + id));
        return userMapper.toProfile(user);
    }

    @Override
    public List<RespondedApplicantDto> getMyResponses(Long authUserId) {
        log.debug("Профиль: мои отклики userId={}", authUserId);
        var resumeIds = resumeRepository.findAllByApplicant_Id(authUserId)
                .stream().map(Resume::getId).toList();

        var responded = resumeIds.isEmpty()
                ? List.<RespondedApplicant>of()
                : respondedApplicantRepository.getRespondedApplicantsByResumesId(resumeIds);

        return responded.stream()
                .map(r -> new RespondedApplicantDto(
                        r.getId(),
                        r.getResume() != null ? r.getResume().getId() : null,
                        r.getVacancy()!= null ? r.getVacancy().getId(): null,
                        r.getConfirmation(),
                        r.getCreatedDate()))
                .toList();
    }

    @Override
    public List<RespondedApplicantDto> getMyVacanciesResponses(Long authUserId, Pageable p) {
        log.debug("Профиль: отклики на мои вакансии employerId={}", authUserId);
        var vacancyIds = vacancyRepository.findList(authUserId, p)
                .map(v -> v.getId())
                .getContent();

        var responses = vacancyIds.isEmpty()
                ? List.<RespondedApplicant>of()
                : respondedApplicantRepository.getRespondedApplicantsByVacancyIds(vacancyIds);

        return responses.stream()
                .map(r -> new RespondedApplicantDto(
                        r.getId(),
                        r.getResume()  != null ? r.getResume().getId()  : null,
                        r.getVacancy() != null ? r.getVacancy().getId() : null,
                        r.getConfirmation(),
                        r.getCreatedDate()))
                .toList();
    }

    @Override
    public List<VacancyListItemDto> getMyShortVacancies(Long userId) {
        log.debug("Профиль: короткий список вакансий userId={}", userId);
        return vacancyRepository.findList(userId, PageRequest.of(0, SHORT_LIMIT)).getContent();
    }

    @Override
    public List<ResumeListItemDto> getMyShortResumes(Long userId) {
        log.debug("Профиль: короткий список резюме userId={}", userId);
        return resumeRepository.findList(userId, PageRequest.of(0, SHORT_LIMIT)).getContent();
    }
}
