CREATE TABLE IF NOT EXISTS person (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      password VARCHAR(255) NOT NULL,
                                      role ENUM('Client', 'Admin', 'Visitor') NOT NULL
);



CREATE TABLE IF NOT EXISTS client (
                                      id BIGINT PRIMARY KEY,
                                      FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS visitor (
                                       id BIGINT PRIMARY KEY,
                                       FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS admin (
                                     id BIGINT PRIMARY KEY,
                                     FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contact (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       first_name VARCHAR(255),
                                       full_name VARCHAR(255),
                                       email VARCHAR(255),
                                       message TEXT,
                                       admin_id BIGINT,
                                       FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE SET NULL
);
