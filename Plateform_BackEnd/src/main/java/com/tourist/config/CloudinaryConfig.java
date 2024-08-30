package com.tourist.config;


import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dstprdlpz");
        config.put("api_key", "247368288385951");
        config.put("api_secret", "tqCAW3i3JwG1gEBCFQxrbY1EL4E");
        return new Cloudinary(config);
    }
}
