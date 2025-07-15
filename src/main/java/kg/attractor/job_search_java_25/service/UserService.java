package kg.attractor.job_search_java_25.service;


import kg.attractor.job_search_java_25.dto.AvatarDto;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public interface UserService {

    @SneakyThrows
    default String saveUploadedFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get("data/" + subDir);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + fileName);
        if (!Files.exists(filePath)) Files.createFile(filePath);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    default ResponseEntity<?> downloadAvatar(String fileName, String subDir, MediaType mediaType) {
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

    ResponseEntity<?> getImageById(String filename);

    void addImage(AvatarDto avatarDto);
}
