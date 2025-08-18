package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.RespondedApplicantDao;
import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final UserDao userDao;

    private final ResumeDao resumeDao;

    private final VacancyDao vacancyDao;

    private final RespondedApplicantDao respondedApplicantDao;

    private final PasswordEncoder passwordEncoder;
    private static final String SUB_DIR = "avatar";


    @Override
    public void addAvatar(AvatarDto avatarDto) {
        // надо проверить, она заменяет или удаляет?
        log.info("Профиль: загрузка аватара userId={}", avatarDto.getUserId());
        String avatar = FileUtil.saveUploadedFile(avatarDto.getAvatar(), SUB_DIR);
        userDao.uploadAvatar(avatar, avatarDto.getUserId());
        log.debug("Аватар сохранён: {}", avatar);
    }

    @Override
    public ResponseEntity<?> getAvatarByUserId(Long userId) {
        log.debug("Профиль: получение аватара userId={}", userId);
        String avatar = userDao.getAvatarByUserId(userId);
        return FileUtil.downloadImage(avatar, SUB_DIR);
    }

    @Override
    public MyProfileDto getMyProfile(Long auth) {

        log.debug("Профиль: getMyProfile(auth={})", auth);
        try {
            User user = userDao.getMyProfile(auth);
            MyProfileDto myProfileDto = new MyProfileDto();
            myProfileDto.setName(user.getName());
            myProfileDto.setSurname(user.getSurname());
            myProfileDto.setAge(user.getAge());
            myProfileDto.setEmail(user.getEmail());
            myProfileDto.setPhoneNumber(user.getPhoneNumber());
            myProfileDto.setAvatar(user.getAvatar());
            log.info("Профиль: отдан профиль userId={}", auth);
//            return ResponseEntity.ok(myProfileDto);
            return myProfileDto;
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new NotFoundException("User id=" + auth);
        }
    }

    @Override
    public MyProfileDto editProfile(EditProfileDto epd, Long authId) {
        log.info("Профиль: редактирование userId={}", authId);

        User user = new User();
        user.setName(epd.getName());
        user.setSurname(epd.getSurname());
        user.setAge(epd.getAge());
        user.setEmail(epd.getEmail());
//        user.setPassword(passwordEncoder.encode(epd.getPassword()));
        user.setPhoneNumber(epd.getPhoneNumber());

        int updated = userDao.editProfile(user, authId);

        if (updated == 0) throw new NotFoundException("User id=" + authId);

        User fresh = userDao.getMyProfile(authId);

        MyProfileDto dto = new MyProfileDto();
        dto.setName(fresh.getName());
        dto.setSurname(fresh.getSurname());
        dto.setAge(fresh.getAge());
        dto.setEmail(fresh.getEmail());
        dto.setPhoneNumber(fresh.getPhoneNumber());
        dto.setAvatar(fresh.getAvatar());

        log.debug("Профиль обновлён userId={}", authId);
        return dto;
    }


    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getMyResponses(Long auth) {

        log.debug("Профиль: мои отклики userId={}", auth);
        List<Resume> resumes = resumeDao.getAllResumesById(auth);
        List<Long> resumeIds = resumes.stream().map(Resume::getId).toList();
        List<RespondedApplicant> responded = respondedApplicantDao.getRespondedApplicantsByResumesId(resumeIds);

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
    public ResponseEntity<List<RespondedApplicantDto>> getMyVacanciesResponses(Long auth) {
        log.debug("Профиль: отклики на мои вакансии employerId={}", auth);

        List<Vacancy> vacancies = vacancyDao.getAllVacanciesById(auth);
        List<Long> vacancyIds = vacancies.stream().map(Vacancy::getId).toList();

        List<RespondedApplicant> responses = respondedApplicantDao.getRespondedApplicantsByVacancyIds(vacancyIds);

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
        return vacancyDao.getShortVacanciesByAuthorId(userId)
                .stream()
                .map(v -> new VacancyShortDto(v.getName(), v.getUpdateTime()))
                .toList();
    }

    @Override
    public List<ResumeShortDto> getMyShortResumes(Long userId) {
        log.debug("Профиль: получение краткого списка резюме userId={}", userId);
        return resumeDao.getShortResumesByApplicantId(userId)
                .stream()
                .map(r -> new ResumeShortDto(r.getName(), r.getUpdateTime()))
                .toList();
    }

}
