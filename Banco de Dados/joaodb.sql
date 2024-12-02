CREATE DATABASE joaodb;

USE joaodb;

CREATE TABLE usuarios (
    id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    data_de_cadastro DATE,
    email VARCHAR(255),
    nivel_acesso VARCHAR(255),
    nome VARCHAR(255),
    senha VARCHAR(255)
);

select * from usuarios;
