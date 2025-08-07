package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.service.ProfileService;
import kg.attractor.job_search_java_25.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserDao userDao;
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
}
