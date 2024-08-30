package com.tourist.service;

import com.cloudinary.Cloudinary;
import com.tourist.dto.CloudinaryResponse;
import com.tourist.exception.FuncErrorException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
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

    public CloudinaryResponse uploadFile(MultipartFile file) {
        try {
            // Upload the file with default settings (public_id and resource_type are not specified)
            final Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "resource_type", "auto" // Automatically determine resource type based on file
            ));
            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (IOException e) {
            throw new FuncErrorException("Failed to upload file");
        }
    }
}
