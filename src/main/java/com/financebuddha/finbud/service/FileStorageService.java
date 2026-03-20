package com.financebuddha.finbud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileStorageService {

    private final S3Client s3Client;

    @Value("${AWS_S3_BUCKET}")
    private String bucketName;

    public FileStorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // ✅ Upload resume to S3 — returns the S3 key (filename)
    public String uploadResume(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Generate unique filename to avoid conflicts
            String originalName = file.getOriginalFilename();
            String extension    = originalName != null && originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf("."))
                    : ".pdf";
            String key = "resumes/" + UUID.randomUUID() + extension;

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

            System.out.println("✅ Uploaded to S3: " + key);
            return key;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("S3 upload failed: " + e.getMessage());
        }
    }

    // ✅ Download resume from S3 — returns InputStream
    public InputStream downloadResume(String key) {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            return s3Client.getObject(getRequest);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("S3 download failed: " + e.getMessage());
        }
    }

    // ✅ Delete resume from S3 when application is deleted
    public void deleteResume(String key) {
        try {
            if (key == null || key.isEmpty()) return;

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteRequest);
            System.out.println("✅ Deleted from S3: " + key);

        } catch (Exception e) {
            System.err.println("S3 delete failed (non-critical): " + e.getMessage());
        }
    }
}