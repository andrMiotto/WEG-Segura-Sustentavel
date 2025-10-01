CREATE DATABASE IF NOT EXISTS railway;
USE railway;

CREATE TABLE IF NOT EXISTS emergencias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    fim DATETIME DEFAULT NULL,
    em_andamento BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS salas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL,
    bloco VARCHAR(50) NOT NULL,
    portaria INT NOT NULL,
    unidade VARCHAR(50) NOT NULL,
    situacao_de_risco BOOLEAN NOT NULL DEFAULT FALSE,
    id_emergencia_atual INT DEFAULT NULL,
    FOREIGN KEY (id_emergencia_atual) REFERENCES emergencias(id)
);

CREATE TABLE IF NOT EXISTS pessoas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cadastro INT NOT NULL UNIQUE,
    tipo ENUM ('Colaborador', 'Visitante', 'Terceiro', 'Desconhecido', 'Outro') NOT NULL DEFAULT 'Outro',
    situacao_de_risco BOOLEAN NOT NULL DEFAULT FALSE,
    id_sala_atual INT NOT NULL,
    id_emergencia_atual INT DEFAULT NULL,
    FOREIGN KEY (id_sala_atual) REFERENCES salas(id),
    FOREIGN KEY (id_emergencia_atual) REFERENCES emergencias(id)
);

CREATE TABLE IF NOT EXISTS salas_emergencias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sala_id INT NOT NULL,
    emergencia_id INT NOT NULL,
    FOREIGN KEY (sala_id) REFERENCES salas(id),
    FOREIGN KEY (emergencia_id) REFERENCES emergencias(id)
);


CREATE OR REPLACE VIEW view_pessoas_em_emergencias AS
SELECT p.id, p.nome, p.cadastro, e.id AS emergencia_id, e.em_andamento
FROM pessoas p
JOIN emergencias e ON p.id_emergencia_atual = e.id;

CREATE OR REPLACE VIEW view_pessoas_por_sala AS
SELECT s.numero AS sala_numero, s.bloco, s.unidade, p.nome AS pessoa_nome, p.tipo
FROM pessoas p
JOIN salas s ON p.id_sala_atual = s.id;

CREATE OR REPLACE VIEW view_pessoa_situacao AS
SELECT
    p.id AS pessoa_id,
    p.nome,
    p.tipo,
    p.situacao_de_risco,
    s.numero AS sala_numero,
    s.bloco,
    s.unidade,
    e.id AS emergencia_id,
    e.em_andamento
FROM pessoas p
JOIN salas s ON p.id_sala_atual = s.id
LEFT JOIN emergencias e ON p.id_emergencia_atual = e.id;

CREATE OR REPLACE VIEW view_emergencias_pessoas AS
SELECT
    e.id AS emergencia_id,
    e.inicio,
    e.fim,
    p.id AS pessoa_id,
    p.nome AS pessoa_nome,
    p.tipo
FROM emergencias e
JOIN pessoas p ON p.id_emergencia_atual = e.id;

CREATE OR REPLACE VIEW view_emergencia_salas AS
SELECT
    e.id AS emergencia_id,
    e.inicio,
    s.id AS sala_id,
    s.numero,
    s.bloco,
    s.unidade,
    s.situacao_de_risco
FROM emergencias e
JOIN salas s ON s.id_emergencia_atual = e.id
WHERE e.em_andamento = TRUE;

DELIMITER //
CREATE TRIGGER trg_finalizar_emergencia
AFTER UPDATE ON emergencias
FOR EACH ROW
BEGIN
    IF OLD.em_andamento = TRUE AND NEW.em_andamento = FALSE THEN
        UPDATE pessoas
        SET situacao_de_risco = FALSE,
            id_emergencia_atual = NULL
        WHERE id_emergencia_atual = NEW.id;

        UPDATE salas
        SET situacao_de_risco = FALSE,
            id_emergencia_atual = NULL
        WHERE id_emergencia_atual = NEW.id;
    END IF;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER trg_atualizacao_riscos
AFTER UPDATE ON salas
FOR EACH ROW
BEGIN
    IF OLD.situacao_de_risco = FALSE AND NEW.situacao_de_risco = TRUE THEN
        UPDATE pessoas
        SET situacao_de_risco = TRUE,
            id_emergencia_atual = NEW.id_emergencia_atual
        WHERE id_sala_atual = NEW.id;
    END IF;
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE registrar_emergencia(
    IN p_titulo VARCHAR(100),
    IN p_descricao TEXT
)
BEGIN
    INSERT INTO emergencias (titulo, descricao, em_andamento)
    VALUES (p_titulo, p_descricao, TRUE);
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE encerrar_emergencia(IN emergencia_id INT)
BEGIN
    UPDATE emergencias
    SET fim = CURRENT_TIMESTAMP,
        em_andamento = FALSE
    WHERE id = emergencia_id;
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE associar_pessoa_emergencia(IN pessoa_id INT, IN emergencia_id INT)
BEGIN
    IF (SELECT situacao_de_risco FROM pessoas WHERE id = pessoa_id) = TRUE THEN
        UPDATE pessoas
        SET id_emergencia_atual = emergencia_id
        WHERE id = pessoa_id;
    END IF;
END;
//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE associar_sala_emergencia(IN sala_id INT, IN emergencia_id INT)
BEGIN
    UPDATE salas
    SET id_emergencia_atual = emergencia_id,
        situacao_de_risco = TRUE
    WHERE id = sala_id;

    INSERT INTO salas_emergencias (sala_id, emergencia_id)
    VALUES (sala_id, emergencia_id);
END;
//
DELIMITER ;

CREATE INDEX idx_emergencias_em_andamento ON emergencias(em_andamento);
CREATE INDEX idx_emergencias_inicio ON emergencias(inicio);

CREATE INDEX idx_salas_numero ON salas(numero);
CREATE INDEX idx_salas_bloco ON salas(bloco);
CREATE INDEX idx_salas_unidade ON salas(unidade);
CREATE INDEX idx_salas_id_emergencia ON salas(id_emergencia_atual);

CREATE INDEX idx_pessoas_cadastro ON pessoas(cadastro);
CREATE INDEX idx_pessoas_tipo ON pessoas(tipo);
CREATE INDEX idx_pessoas_id_sala ON pessoas(id_sala_atual);
CREATE INDEX idx_pessoas_id_emergencia ON pessoas(id_emergencia_atual);

CREATE INDEX idx_salas_emergencias_sala_id ON salas_emergencias(sala_id);
CREATE INDEX idx_salas_emergencias_emergencia_id ON salas_emergencias(emergencia_id);