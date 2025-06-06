CREATE TABLE user_db
(
    id           BIGSERIAL PRIMARY KEY,
    file_name    VARCHAR(255),
    content_type VARCHAR(255),
    data         BYTEA
    );