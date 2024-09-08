
CREATE TABLE contact (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(255) NOT NULL,
                         full_name VARCHAR(255),
                         email VARCHAR(255) NOT NULL UNIQUE,
                         message TEXT
);
