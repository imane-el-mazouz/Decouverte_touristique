package com.tourist.service;

import com.cloudinary.Cloudinary;
import com.tourist.dto.CloudinaryResponse;
import com.tourist.exception.FuncErrorException;
import com.tourist.exception.ImageUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

//    public CloudinaryService(Cloudinary cloudinary) {
//        this.cloudinary = cloudinary;
//    }

    public CloudinaryService(@Value("${cloudinary.cloud-name}") String cloudName,
                             @Value("${cloudinary.api-key}") String apiKey,
                             @Value("${cloudinary.api-secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }


    public CloudinaryResponse uploadFile(MultipartFile file, String fileName, String resourceType) {
        try {
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "public_id", "tourist/" + fileName,
                    "resource_type", resourceType
            ));
            String url = (String) result.get("secure_url");
            String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder()
                    .publicId(publicId)
                    .url(url)
                    .build();
        } catch (IOException e) {
            throw new FuncErrorException("Failed to upload file due to I/O error");
        } catch (Exception e) {
            throw new FuncErrorException("Failed to upload file due to an unexpected error");
        }
    }

    public List<String> uploadFile(MultipartFile[] images) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), new HashMap<>());
                imageUrls.add((String) uploadResult.get("url"));
            } catch (IOException e) {
                throw new ImageUploadException("Error uploading image: " + image.getOriginalFilename(), e);
            } catch (Exception e) {
                throw new ImageUploadException("An unexpected error occurred while uploading image: " + image.getOriginalFilename(), e);
            }
        }
        return imageUrls;
    }

}
