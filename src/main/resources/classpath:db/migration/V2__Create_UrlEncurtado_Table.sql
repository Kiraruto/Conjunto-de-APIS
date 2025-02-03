CREATE TABLE url_encurtado (
    id SERIAL PRIMARY KEY,
    long_url VARCHAR(255) NOT NULL,
    shortened_url VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES usuario(id)
);