CREATE TABLE hotel (
                       idHotel BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       img VARCHAR(255),
                       location VARCHAR(255),
                       category VARCHAR(255),
                       average_rating DOUBLE,
                       price DOUBLE,
                       distance DOUBLE
);

CREATE TABLE room (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      type VARCHAR(255),
                      price BIGINT,
                      available BOOLEAN,
                      hotel_id BIGINT,
                      FOREIGN KEY (hotel_id) REFERENCES hotel(idHotel) ON DELETE SET NULL
);

-- Création de la table Image
CREATE TABLE image (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       image_url VARCHAR(255),
                       cloudinary_image_id VARCHAR(255),
                       room_id BIGINT,
                       FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE reservation (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             number_of_person BIGINT,
                             date_time DATE,
                             check_in_date DATE,
                             check_out_date DATE,
                             reserved_room BOOLEAN,
                             status VARCHAR(255),
                             excursion_id BIGINT,
                             client_id BIGINT,
                             event_id BIGINT,
                             room_id BIGINT,
                             FOREIGN KEY (excursion_id) REFERENCES excursion(id_excursion) ON DELETE SET NULL,
                             FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE SET NULL,
                             FOREIGN KEY (event_id) REFERENCES event(id_event) ON DELETE SET NULL,
                             FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE SET NULL
);
