CREATE TABLE tradition (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           city VARCHAR(255) NOT NULL,
                           description TEXT
);

CREATE TABLE blog (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT,
                      tradition_id BIGINT,
                      CONSTRAINT fk_tradition_blog
                          FOREIGN KEY (tradition_id) REFERENCES tradition(id) ON DELETE CASCADE
);
