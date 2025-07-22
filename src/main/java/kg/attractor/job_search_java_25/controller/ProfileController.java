package kg.attractor.job_search_java_25.controller;

import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("profile")
public class ProfileController {
    private final ImageService imageService;

    @GetMapping("avatar")
    public ResponseEntity<?> getImage(@RequestParam(name = "filename") String filename) {
        return imageService.getImageById(filename);
    }

    @PostMapping("avatar")
    public ResponseEntity<?> addImage(AvatarDto avatarDto) {
        imageService.addImage(avatarDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
