package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.UserDao;
import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.exceptions.EntityNotFoundException;
import kg.attractor.job_search_java_25.model.User;
import kg.attractor.job_search_java_25.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static kg.attractor.job_search_java_25.util.FileUtil.downloadImage;
import static kg.attractor.job_search_java_25.util.FileUtil.saveUploadedFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final UserDao userDao;
    @Override
    public ResponseEntity<?> getImageById(String filename, String folderName) {
        return downloadImage(filename, folderName);
    }

    @Override
    public void addImage(AvatarDto avatarDto) {
        Long userId = avatarDto.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("userId обязателен");
        }

        User user = userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId));

        String oldAvatar = user.getAvatar();
        if (oldAvatar != null && !oldAvatar.isBlank()) {
            Path oldAvatarPath = Paths.get("data/avatar").resolve(oldAvatar);
            try {
                Files.deleteIfExists(oldAvatarPath);
                log.info("Удалён старый аватар: {}", oldAvatarPath);
            } catch (IOException e) {
                log.warn("Не удалось удалить старый аватар: {}", oldAvatarPath, e);
            }
        }

        String filename = saveUploadedFile(avatarDto.getFile(), "avatar");

        userDao.updateAvatar(userId, filename);
        log.info("Пользователю ID {} установлен аватар: {}", userId, filename);
    }
}
