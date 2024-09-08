CREATE TABLE event (
                       id_event BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       img_path VARCHAR(255),
                       date DATE,
                       location VARCHAR(255),
                       capacity INT,
                       price DOUBLE,
                       rating INT,
                       distance DOUBLE,
                       category VARCHAR(225) NOT NULL,
                       CONSTRAINT FK_event_reservation FOREIGN KEY (id_event) REFERENCES reservation(id)
);
