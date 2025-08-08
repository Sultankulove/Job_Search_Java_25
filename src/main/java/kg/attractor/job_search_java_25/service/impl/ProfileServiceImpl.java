package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.RespondedApplicantDao;
import kg.attractor.job_search_java_25.dao.ResumeDao;
import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.*;
import kg.attractor.job_search_java_25.model.RespondedApplicant;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.model.Vacancy;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserDao userDao;

    private final ResumeDao resumeDao;

    private final VacancyDao vacancyDao;

    private final RespondedApplicantDao respondedApplicantDao;
    private static final String SUB_DIR = "avatar";


    @Override
    public void addAvatar(AvatarDto avatarDto) {
        // надо проверить, она заменяет или удаляет?
        String avatar = FileUtil.saveUploadedFile(avatarDto.getAvatar(), SUB_DIR);
        userDao.uploadAvatar(avatar, avatarDto.getUserId());
    }

    @Override
    public ResponseEntity<?> getAvatarByUserId(Long userId) {
        String avatar = userDao.getAvatarByUserId(userId);
        return FileUtil.downloadImage(avatar, SUB_DIR);
    }

    @Override
    public ResponseEntity<MyProfileDto> getMyProfile(Long auth) {
        User user = userDao.getMyProfile(auth);
        MyProfileDto myProfileDto = new MyProfileDto();
        myProfileDto.setName(user.getName());
        myProfileDto.setSurname(user.getSurname());
        myProfileDto.setAge(user.getAge());
        myProfileDto.setEmail(user.getEmail());
        myProfileDto.setPhoneNumber(user.getPhoneNumber());
        myProfileDto.setAvatar(user.getAvatar());
        return ResponseEntity.ok(myProfileDto);
    }

    @Override
    public ResponseEntity<Void> editProfile(EditProfileDto epd, Long auth) {
        User user = new User();
        user.setName(epd.getName());
        user.setSurname(epd.getSurname());
        user.setAge(epd.getAge());
        user.setEmail(epd.getEmail());
        user.setPassword(epd.getPassword());
        user.setPhoneNumber(epd.getPhoneNumber());
        userDao.editProfile(user, auth);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getMyResponses(Long auth) {
        List<Resume> resumes = resumeDao.getAllResumesById(auth);
        List<Long> resumeIds = resumes.stream().map(Resume::getId).toList();
        List<RespondedApplicant> responded = respondedApplicantDao.getRespondedApplicantsByResumesId(resumeIds);

        List<RespondedApplicantDto> dtos = responded.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResumeId())
                        .vacancyId(entity.getVacancyId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<List<RespondedApplicantDto>> getMyVacanciesResponses(Long auth) {

        List<Vacancy> vacancies = vacancyDao.getAllVacanciesById(auth);
        List<Long> vacancyIds = vacancies.stream().map(Vacancy::getId).toList();

        List<RespondedApplicant> responses = respondedApplicantDao.getRespondedApplicantsByVacancyIds(vacancyIds);

        List<RespondedApplicantDto> dtos = responses.stream()
                .map(entity -> RespondedApplicantDto.builder()
                        .id(entity.getId())
                        .resumeId(entity.getResumeId())
                        .vacancyId(entity.getVacancyId())
                        .confirmation(entity.getConfirmation())
                        .build())
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
