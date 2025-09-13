package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.responseDto.RespondedApplicantDto;
import kg.attractor.job_search_java_25.dto.userDtos.AccountType;
import kg.attractor.job_search_java_25.dto.userDtos.AvatarDto;
import kg.attractor.job_search_java_25.dto.userDtos.EditProfileDto;
import kg.attractor.job_search_java_25.dto.userDtos.UserProfileDto;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.mappers.UserMapper;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.repository.RespondedApplicantRepository;
import kg.attractor.job_search_java_25.repository.ResumeRepository;
import kg.attractor.job_search_java_25.repository.UserRepository;
import kg.attractor.job_search_java_25.repository.VacancyRepository;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private static final String SUB_DIR = "avatar";
    private final ResumeRepository resumeRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;


    @Override
    public void addAvatar(AvatarDto avatarDto) {

        log.info("Профиль: загрузка аватара userId={}", avatarDto.getUserId());
        String avatar = FileUtil.saveUploadedFile(avatarDto.getAvatar(), SUB_DIR);
        userRepository.saveAvatar(avatar, avatarDto.getUserId());
        log.debug("Аватар сохранён: {}", avatar);
    }

    @Override
    public ResponseEntity<?> findAvatarById(Long userId) {
        log.debug("Профиль: получение аватара userId={}", userId);
        UserProfileDto avatar = userRepository.findAvatarById(userId);
        String avatarStr = avatar.getAvatar();
        return FileUtil.downloadImage(avatarStr, SUB_DIR);
    }

    @Override
    public UserProfileDto getMyProfile(Long id) {
        try {
            Optional<UserProfileDto> user = userRepository.getUserById(id);
            UserProfileDto userProfileDto = new UserProfileDto();
            return userProfileDto;
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new NotFoundException("User id=" + id);
        }
    }

    @Transactional
    @Override
    public UserProfileDto updateProfileByUserId(EditProfileDto dto, Long id) {
        UserProfileDto userProfileDto = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserProfileDto id=" + id));

        UserProfileDto getUserProfileDto = userRepository.save(userProfileDto);


        return UserMapper.toDto(getUserProfileDto);
    }





    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getMyResponses(Long auth) {

        log.debug("Профиль: мои отклики userId={}", auth);
        List<Resume> resumes = resumeRepository.findAllByApplicant_Id(auth);
        List<Long> resumeIds = resumes.stream().map(Resume::getId).toList();
        List<RespondedApplicant> responded = respondedApplicantRepository.getRespondedApplicantsByResumesId(resumeIds);

        List<RespondedApplicantDto> dtos = responded.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResume().getId())
                        .vacancyId(entity.getVacancy().getId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();

        log.info("Профиль: найдено {} откликов по {} резюме", dtos.size(), resumeIds.size());
        return ResponseEntity.ok(dtos);    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getMyVacanciesResponses(Long auth, Pageable p) {
        log.debug("Профиль: отклики на мои вакансии employerId={}", auth);

        Page<Vacancy> vacancies = vacancyRepository.findAllByAuthor_Id(auth, p);
        List<Long> vacancyIds = vacancies.stream().map(Vacancy::getId).toList();

        List<RespondedApplicant> responses = respondedApplicantRepository.getRespondedApplicantsByVacancyIds(vacancyIds);

        List<RespondedApplicantDto> dtos = responses.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResume().getId())
                        .vacancyId(entity.getVacancy().getId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();

        log.debug("Профиль: отклики на мои вакансии employerId={}", auth);
        return ResponseEntity.ok(dtos);
    }

    @Override
    public List<VacancyShortDto> getMyShortVacancies(Long userId) {
        log.debug("Профиль: получение краткого списка вакансий userId={}", userId);

        return vacancyRepository.getShortVacanciesByAuthorId(userId)
                .stream()
                .map(v -> new VacancyShortDto(v.getName(), v.getUpdateTime()))
                .toList();
    }

    @Override
    public List<ResumeShortDto> getMyShortResumes(Long userId) {
        log.debug("Профиль: получение краткого списка резюме userId={}", userId);
        return resumeRepository.getShortResumesByApplicantId(userId)
                .stream()
                .map(r -> new ResumeShortDto(r.getName(), r.getUpdateTime()))
                .toList();
    }

    @Override
    public UserProfileDto getUserProfile(Long id) {

        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("UserProfileDto not found"));

        return UserProfileDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(AccountType.valueOf(user.getAccountType()))
                .build();
    }
}
