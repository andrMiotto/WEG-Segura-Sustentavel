CREATE DATABASE IF NOT EXISTS weg_segura;
USE weg_segura;

CREATE TABLE IF NOT EXISTS pessoas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    id_cartao_rfid VARCHAR(50) UNIQUE NOT NULL,
    foto_url VARCHAR(255),
    consentimento_foto BOOLEAN DEFAULT FALSE,
    presente BOOLEAN DEFAULT FALSE,
    tipo ENUM('Aluno', 'Funcionario', 'Visitante') DEFAULT 'Aluno',
    criado_em DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS registros_acesso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pessoa_id INT NOT NULL,
    tipo ENUM('ENTRADA', 'SAIDA') NOT NULL,
    local ENUM('Sala', 'Corredor', 'Outro') NOT NULL,
    data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
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

CREATE TABLE IF NOT EXISTS notificacoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pessoa_id INT,
    tipo ENUM('ACESSO_INDEVIDO', 'EMERGENCIA', 'SAIDA_NORMAL', 'ALERTA_SEGURANCA'),
    data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    canal ENUM('EMAIL', 'TELEGRAM', 'WHATSAPP'),
    mensagem TEXT,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);

/* Trigger para atualizar presença automaticamente
CREATE TRIGGER atualizar_presenca
AFTER INSERT ON registros_acesso
FOR EACH ROW
BEGIN
    IF NEW.tipo = 'ENTRADA' THEN
        UPDATE pessoas SET presente = TRUE WHERE id = NEW.pessoa_id;
    ELSEIF NEW.tipo = 'SAIDA' THEN
        UPDATE pessoas SET presente = FALSE WHERE id = NEW.pessoa_id;
    END IF;
END;
*/

-- View de pessoas presentes na sala
CREATE OR REPLACE VIEW pessoas_presentes AS
SELECT id, nome, tipo FROM pessoas WHERE presente = TRUE;

-- View de situação atual da emergência
CREATE OR REPLACE VIEW status_emergencia AS
SELECT e.id AS emergencia_id, e.inicio, e.status,
       COUNT(DISTINCT le.pessoa_id) AS pessoas_restantes
FROM emergencias e
LEFT JOIN logs_emergencia le ON e.id = le.emergencia_id AND le.saiu = FALSE
GROUP BY e.id;

-- Procedure para iniciar emergência
DELIMITER //
CREATE PROCEDURE iniciar_emergencia()
BEGIN
    INSERT INTO emergencias (inicio, status) VALUES (NOW(), 'ATIVA');
END;
//
DELIMITER ;

-- Procedure para encerrar emergência
DELIMITER //
CREATE PROCEDURE encerrar_emergencia(IN emergenciaId INT)
BEGIN
    UPDATE emergencias SET fim = NOW(), status = 'ENCERRADA' WHERE id = emergenciaId;
END;
//
DELIMITER ;

-- Procedure para registrar saída de pessoa em emergência
DELIMITER //
CREATE PROCEDURE registrar_saida_emergencia(IN pessoaId INT)
BEGIN
    DECLARE emergenciaId INT;
    SELECT id INTO emergenciaId FROM emergencias WHERE status = 'ATIVA' ORDER BY inicio DESC LIMIT 1;
    
    IF emergenciaId IS NOT NULL THEN
        INSERT INTO logs_emergencia (emergencia_id, pessoa_id, data_ultima_localizacao, local, saiu)
        VALUES (emergenciaId, pessoaId, NOW(), 'Sala', TRUE);
    END IF;
END;
//
DELIMITER ;