CREATE TABLE roteiro_de_viagens (
    id SERIAL PRIMARY KEY,
    destination VARCHAR(255) NOT NULL,
    dias INT NOT NULL
);