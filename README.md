Protótipo FIGMA:
https://www.figma.com/proto/8b6gqJrUBS47am4UZLhKf3/Untitled?node-id=0-1&t=U5lBTc0ssO9Tv3VS-1

Banco de Dados:

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
