CREATE DATABASE IF NOT EXISTS weg_segura;
USE weg_segura;

CREATE TABLE IF NOT EXISTS sala (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL,
    bloco VARCHAR(50) NOT NULL,
    unidade VARCHAR(50) NOT NULL,
    portaria INT NOT NULL
);

CREATE TABLE IF NOT EXISTS pessoas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cadastro INT UNIQUE,
    tipo ENUM ('Colaborador', 'Visitante', 'Terceiro', 'Desconhecido', 'Outro') NOT NULL
);

/*terminar*/

CREATE TABLE IF NOT EXISTS registros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pessoa_id INT NOT NULL,
    sala_id INT NOT NULL,
    tipo_movimentacao VARCHAR(100) NOT NULL,
    timestamp_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id),
    FOREIGN KEY (sala_id) REFERENCES salas(id)
);

CREATE TABLE IF NOT EXISTS emergencias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    inicio DATETIME NOT NULL,
    fim DATETIME,
    status ENUM('ATIVA', 'ENCERRADA') DEFAULT 'ATIVA'
);

CREATE TABLE IF NOT EXISTS logs_emergencia (
    id INT PRIMARY KEY AUTO_INCREMENT,
    emergencia_id INT,
    pessoa_id INT,
    data_ultima_localizacao DATETIME,
    local VARCHAR(100),
    saiu BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (emergencia_id) REFERENCES emergencias(id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);