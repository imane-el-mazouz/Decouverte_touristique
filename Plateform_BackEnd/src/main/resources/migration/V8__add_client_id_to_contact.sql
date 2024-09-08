ALTER TABLE contact
    ADD COLUMN client_id BIGINT;

ALTER TABLE contact
    ADD CONSTRAINT fk_client
        FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE SET NULL;
