
CREATE TABLE review (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        rating BIGINT,
                        comment VARCHAR(255),
                        date DATE,
                        client_id BIGINT,
                        reservation_id BIGINT,
                        FOREIGN KEY (client_id) REFERENCES client(id),
                        FOREIGN KEY (reservation_id) REFERENCES reservation(id)
);
