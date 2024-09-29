CREATE TABLE IF NOT EXISTS room_image (
                                          room_id BIGINT,
                                          image_url VARCHAR(255),
    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
    );