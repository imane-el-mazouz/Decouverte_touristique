CREATE TABLE excursion (
                           id_excursion BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           description TEXT NOT NULL,
                           img_path VARCHAR(255),
                           date_time TIMESTAMP NOT NULL,
                           location VARCHAR(255) NOT NULL,
                           capacity INT NOT NULL,
                           rating BIGINT,
                           reservation_id BIGINT,
                           CONSTRAINT fk_reservation_excursion
                               FOREIGN KEY (reservation_id)
                                   REFERENCES reservation(id)
                                   ON DELETE CASCADE
);
