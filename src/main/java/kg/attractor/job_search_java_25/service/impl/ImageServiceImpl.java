package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.AvatarDto;
import kg.attractor.job_search_java_25.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Override
    public ResponseEntity<?> getImageById(String filename) {
        return downloadAvatar(filename, "avatar", MediaType.IMAGE_JPEG);
    }

    @Override
    public void addImage(AvatarDto avatarDto) {

        String filename = saveUploadedFile(avatarDto.getFile(), "avatar");
        System.out.println(filename);
    }

    @SneakyThrows
    private String saveUploadedFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String filename = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get("data/" + subDir);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + filename);
        if (!Files.exists(filePath)) Files.createFile(filePath);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    private ResponseEntity<?> downloadAvatar(String fileName, String subDir, MediaType mediaType) {
        try {
            byte[] image = Files.readAllBytes(Paths.get("data/" + subDir + "/" + fileName));

            Resource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
        }
    }
}
