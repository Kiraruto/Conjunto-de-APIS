CREATE TABLE dias_roteiro (
    id SERIAL PRIMARY KEY,
    dia INT NOT NULL,
    roteiro_de_viagens_id BIGINT NOT NULL,
    CONSTRAINT fk_roteiro_de_viagens FOREIGN KEY (roteiro_de_viagens_id) REFERENCES roteiro_de_viagens(id)
);

CREATE TABLE atividades (
    dia_roteiro_id BIGINT NOT NULL,
    atividade VARCHAR(255) NOT NULL,
    CONSTRAINT fk_dia_roteiro FOREIGN KEY (dia_roteiro_id) REFERENCES dias_roteiro(id)
);