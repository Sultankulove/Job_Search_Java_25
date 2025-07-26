package kg.attractor.job_search_java_25.util;

import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
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

public class FileUtil {
    @SneakyThrows
    public static String saveUploadedFile(MultipartFile file, String subDir) {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || !originalFilename.matches("^[\\w\\-. ]+$")) {
            throw new IllegalArgumentException("Suspicious file name!");
        }

        String filename = UUID.randomUUID() + "_" + originalFilename;

        Path basePath = Paths.get("data").toAbsolutePath().normalize();
        Path pathDir = basePath.resolve(subDir).normalize();

        if (!pathDir.startsWith(basePath)) {
            throw new SecurityException("Path traversal attempt detected!");
        }

        Files.createDirectories(pathDir);
        Path filePath = pathDir.resolve(filename);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        }
        return filename;
    }

    public static ResponseEntity<?> downloadImage(String fileName, String subDir) {
        try {
            Path basePath = Paths.get("data").toAbsolutePath().normalize();
            Path filePath = basePath.resolve(Paths.get(subDir)).resolve(fileName).normalize();

            if (!filePath.startsWith(basePath)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Неверный путь к файлу");
            }

            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не найден");
            }

            byte[] fileBytes = Files.readAllBytes(filePath);
            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке файла");
        }
    }
}
