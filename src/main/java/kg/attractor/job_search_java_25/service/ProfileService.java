package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.AvatarDto;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    void addAvatar(AvatarDto avatarDto);

    ResponseEntity<?> getAvatarByUserId(Long userId);
}
