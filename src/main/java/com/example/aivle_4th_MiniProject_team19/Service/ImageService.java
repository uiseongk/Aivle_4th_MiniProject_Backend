package com.example.aivle_4th_MiniProject_team19.Service;

import com.example.aivle_4th_MiniProject_team19.Entity.Image;
import com.example.aivle_4th_MiniProject_team19.Repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${app.upload-dir}")
    private String baseDir;   // ⭐ 여기로 변경

    public Image saveImageFromUrl(String input) throws IOException {

        String fileName = UUID.randomUUID() + ".png";

        Path uploadPath = Paths.get(baseDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        // CASE 1: Base64
        if (input.startsWith("data:image")) {
            saveBase64(input, filePath);
        }
        // CASE 2: URL
        else {
            saveFromUrl(input, filePath);
        }

        Image image = new Image();
        image.setOriginFileName(input);
        image.setModifiedFileName(fileName);

        return imageRepository.save(image);
    }

    private void saveFromUrl(String originUrl, Path filePath) throws IOException {
        URL url = new URL(originUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (InputStream inputStream = connection.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void saveBase64(String base64, Path filePath) throws IOException {
        String base64Data = base64.substring(base64.indexOf(",") + 1);
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        Files.write(filePath, decodedBytes);
    }

    public Image updateImage(Long id, String input) throws IOException {

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found: " + id));

        String fileName = image.getModifiedFileName();
        if (fileName == null) {
            fileName = UUID.randomUUID() + ".png";
        }

        Path uploadPath = Paths.get(baseDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        if (input.startsWith("data:image")) {
            saveBase64(input, filePath);
        } else {
            saveFromUrl(input, filePath);
        }

        image.setOriginFileName(input);
        image.setModifiedFileName(fileName);

        return imageRepository.save(image);
    }
}

