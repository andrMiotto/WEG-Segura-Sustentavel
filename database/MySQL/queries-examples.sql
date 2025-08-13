-- ========================================
-- EXEMPLOS DE CONSULTAS SQL PARA MYSQL
-- Projeto WEG Segura - MySQL 8.0
-- ========================================

-- 1. LISTAR TODAS AS EMERGÊNCIAS ATIVAS
SELECT 
    id,
    titulo,
    descricao,
    inicio,
    em_andamento
FROM emergencias 
WHERE em_andamento = TRUE
ORDER BY inicio DESC;

-- 2. CONTAR PESSOAS POR TIPO
SELECT 
    tipo,
    COUNT(*) as total_pessoas
FROM pessoas 
GROUP BY tipo
ORDER BY total_pessoas DESC;

-- 3. SALAS EM SITUAÇÃO DE RISCO
SELECT 
    s.id,
    s.numero,
    s.bloco,
    s.unidade,
    e.titulo as emergencia_atual
FROM salas s
LEFT JOIN emergencias e ON s.id_emergencia_atual = e.id
WHERE s.situacao_de_risco = TRUE;

-- 4. PESSOAS EM SALAS COM EMERGÊNCIAS
SELECT 
    p.nome,
    p.tipo,
    s.numero as sala,
    s.bloco,
    e.titulo as emergencia
FROM pessoas p
JOIN salas s ON p.id_sala_atual = s.id
JOIN emergencias e ON s.id_emergencia_atual = e.id
WHERE e.em_andamento = TRUE;

-- 5. HISTÓRICO DE EMERGÊNCIAS POR MÊS
SELECT 
    YEAR(inicio) as ano,
    MONTH(inicio) as mes,
    COUNT(*) as total_emergencias,
    AVG(TIMESTAMPDIFF(MINUTE, inicio, COALESCE(fim, NOW()))) as duracao_media_minutos
FROM emergencias 
GROUP BY YEAR(inicio), MONTH(inicio)
ORDER BY ano DESC, mes DESC;

-- 6. SALAS MAIS AFETADAS POR EMERGÊNCIAS
SELECT 
    s.numero,
    s.bloco,
    COUNT(se.emergencia_id) as total_emergencias
FROM salas s
LEFT JOIN salas_emergencias se ON s.id = se.sala_id
GROUP BY s.id, s.numero, s.bloco
ORDER BY total_emergencias DESC;

-- 7. PESSOAS QUE MAIS MUDARAM DE SALA
SELECT 
    p.nome,
    p.tipo,
    COUNT(DISTINCT p.id_sala_atual) as total_salas_diferentes
FROM pessoas p
GROUP BY p.id, p.nome, p.tipo
HAVING total_salas_diferentes > 1
ORDER BY total_salas_diferentes DESC;

-- 8. EMERGÊNCIAS POR UNIDADE ADMINISTRATIVA
SELECT 
    s.unidade,
    COUNT(DISTINCT e.id) as total_emergencias,
    COUNT(DISTINCT s.id) as salas_afetadas
FROM salas s
JOIN salas_emergencias se ON s.id = se.sala_id
JOIN emergencias e ON se.emergencia_id = e.id
GROUP BY s.unidade
ORDER BY total_emergencias DESC;

-- 9. VISÃO GERAL DO SISTEMA (DASHBOARD)
SELECT 
    (SELECT COUNT(*) FROM emergencias WHERE em_andamento = TRUE) as emergencias_ativas,
    (SELECT COUNT(*) FROM salas WHERE situacao_de_risco = TRUE) as salas_em_risco,
    (SELECT COUNT(*) FROM pessoas WHERE situacao_de_risco = TRUE) as pessoas_em_risco,
    (SELECT COUNT(*) FROM salas) as total_salas,
    (SELECT COUNT(*) FROM pessoas) as total_pessoas;

-- 10. ÚLTIMAS ATIVIDADES DO SISTEMA
SELECT 
    'Emergência' as tipo,
    titulo as descricao,
    inicio as timestamp
FROM emergencias 
WHERE inicio >= DATE_SUB(NOW(), INTERVAL 24 HOUR)
UNION ALL
SELECT 
    'Pessoa em Risco' as tipo,
    CONCAT(nome, ' - ', tipo) as descricao,
    NOW() as timestamp
FROM pessoas 
WHERE situacao_de_risco = TRUE
ORDER BY timestamp DESC
LIMIT 20;

-- ========================================
-- CONSULTAS PARA RELATÓRIOS
-- ========================================

-- RELATÓRIO: RESUMO DIÁRIO
SELECT 
    DATE(inicio) as data,
    COUNT(*) as emergencias_iniciadas,
    COUNT(CASE WHEN fim IS NOT NULL THEN 1 END) as emergencias_finalizadas,
    AVG(TIMESTAMPDIFF(MINUTE, inicio, COALESCE(fim, NOW()))) as duracao_media_minutos
FROM emergencias 
WHERE inicio >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
GROUP BY DATE(inicio)
ORDER BY data DESC;

-- RELATÓRIO: ANÁLISE POR BLOCO
SELECT 
    s.bloco,
    COUNT(DISTINCT s.id) as total_salas,
    COUNT(DISTINCT CASE WHEN s.situacao_de_risco = TRUE THEN s.id END) as salas_em_risco,
    COUNT(DISTINCT p.id) as total_pessoas,
    COUNT(DISTINCT CASE WHEN p.situacao_de_risco = TRUE THEN p.id END) as pessoas_em_risco
FROM salas s
LEFT JOIN pessoas p ON s.id = p.id_sala_atual
GROUP BY s.bloco
ORDER BY s.bloco;

-- RELATÓRIO: EFICIÊNCIA DE RESPOSTA
SELECT 
    e.titulo,
    e.inicio,
    e.fim,
    TIMESTAMPDIFF(MINUTE, e.inicio, COALESCE(e.fim, NOW())) as duracao_minutos,
    CASE 
        WHEN TIMESTAMPDIFF(MINUTE, e.inicio, COALESCE(e.fim, NOW())) <= 30 THEN 'Excelente'
        WHEN TIMESTAMPDIFF(MINUTE, e.inicio, COALESCE(e.fim, NOW())) <= 60 THEN 'Boa'
        WHEN TIMESTAMPDIFF(MINUTE, e.inicio, COALESCE(e.fim, NOW())) <= 120 THEN 'Regular'
        ELSE 'Necessita Melhoria'
    END as classificacao_resposta
FROM emergencias e
ORDER BY duracao_minutos DESC;
