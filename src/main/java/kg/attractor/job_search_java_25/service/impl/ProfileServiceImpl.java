package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {


    private final UserRepository userRepository;

    private final VacancyRepository vacancyRepository;

    private final PasswordEncoder passwordEncoder;
    private static final String SUB_DIR = "avatar";
    private final ResumeRepository resumeRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;


    @Override
    public void addAvatar(AvatarDto avatarDto) {
        // надо проверить, она заменяет или удаляет?
        log.info("Профиль: загрузка аватара userId={}", avatarDto.getUserId());
        String avatar = FileUtil.saveUploadedFile(avatarDto.getAvatar(), SUB_DIR);
        userRepository.uploadAvatar(avatar, avatarDto.getUserId());
        log.debug("Аватар сохранён: {}", avatar);
    }

    @Override
    public ResponseEntity<?> getAvatarByUserId(Long userId) {
        log.debug("Профиль: получение аватара userId={}", userId);
        String avatar = userRepository.getAvatarByUserId(userId);
        return FileUtil.downloadImage(avatar, SUB_DIR);
    }

    @Override
    public MyProfileDto getMyProfile(Long id) {

        log.debug("Профиль: getMyProfile(auth={})", id);
        try {
            MyProfileDto myProfileDto = userRepository.getMyProfile(id);
            return myProfileDto;
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new NotFoundException("User id=" + id);
        }
    }

    @Override
    public MyProfileDto editProfile(EditProfileDto dto, Long authId) {
        log.info("Профиль: редактирование userId={}", authId);

        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());


        User u = userRepository.findById(authId).orElseThrow();
        u.setName(dto.getName());
        u.setSurname(dto.getSurname());
        u.setAge(dto.getAge());
        u.setEmail(dto.getEmail());
        u.setPhoneNumber(dto.getPhoneNumber());
        userRepository.save(u);

        if (authId == 0) throw new NotFoundException("User id=" + authId);

        User fresh = userRepository.findUserById(authId);

        MyProfileDto dto1 = new MyProfileDto();
        dto1.setName(fresh.getName());
        dto1.setSurname(fresh.getSurname());
        dto1.setAge(fresh.getAge());
        dto1.setEmail(fresh.getEmail());
        dto1.setPhoneNumber(fresh.getPhoneNumber());
        dto1.setAvatar(fresh.getAvatar());

        log.debug("Профиль обновлён userId={}", authId);
        return dto1;
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

}
