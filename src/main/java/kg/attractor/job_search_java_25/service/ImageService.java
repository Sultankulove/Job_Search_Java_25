package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.AvatarDto;
import org.springframework.http.ResponseEntity;

public interface ImageService {
    ResponseEntity<?> getImageById(String filename);

    void addImage(AvatarDto avatarDto);
}
