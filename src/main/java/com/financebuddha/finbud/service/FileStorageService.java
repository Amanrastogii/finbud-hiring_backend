package com.financebuddha.finbud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads/}")
    private String uploadDir;

    public String uploadResume(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();

            File savedFile = new File(dir, filename);

            try (FileOutputStream fos = new FileOutputStream(savedFile)) {
                fos.write(file.getBytes());
            }

            System.out.println("Saved at: " + savedFile.getAbsolutePath());

            return filename;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
}