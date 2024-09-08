CREATE TABLE image (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       image_url VARCHAR(255),
                       cloudinary_image_id VARCHAR(255),
                       room_id BIGINT,
                       FOREIGN KEY (room_id) REFERENCES room(id)
);
