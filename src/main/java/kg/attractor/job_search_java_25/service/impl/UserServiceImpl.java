package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<?> getImageById(String filename) {
        return downloadAvatar(filename, "avatar", MediaType.IMAGE_JPEG);
    }

    @Override
    public void addImage(AvatarDto avatarDto) {

        String filename = saveUploadedFile(avatarDto.getFile(), "avatar");
        System.out.println(filename);
    }
}
