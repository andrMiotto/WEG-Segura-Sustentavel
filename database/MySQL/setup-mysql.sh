#!/bin/bash

echo "🚀 Configurando MySQL para WEG Segura..."

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Inicie o Docker primeiro."
    exit 1
fi

# Para e remove container existente se houver
echo "🔄 Parando container existente..."
docker stop weg-segura-mysql 2>/dev/null || true
docker rm weg-segura-mysql 2>/dev/null || true

# Inicia o MySQL
echo "🐳 Iniciando MySQL..."
docker-compose up -d

# Aguarda o MySQL estar pronto
echo "⏳ Aguardando MySQL inicializar..."
sleep 30

# Verifica se está rodando
if docker ps | grep -q "weg-segura-mysql"; then
    echo "✅ MySQL iniciado com sucesso!"
    echo ""
    echo "📊 Informações de acesso:"
    echo "   Host: localhost"
    echo "   Porta: 3306"
    echo "   Usuário root: root"
    echo "   Senha root: wegsegura123"
    echo "   Usuário: weg_user"
    echo "   Senha: wegsegura123"
    echo "   Banco: weg_segura"
    echo ""
    echo "🔗 Para conectar via linha de comando:"
    echo "   mysql -h localhost -P 3306 -u weg_user -p weg_segura"
    echo ""
    echo "📁 Scripts SQL carregados automaticamente:"
    echo "   - database_v1.sql (schema completo)"
    echo "   - database_reduzido.sql (schema simplificado)"
else
    echo "❌ Erro ao iniciar MySQL"
    exit 1
fi

# Testa conexão
echo "🧪 Testando conexão..."
if docker exec weg-segura-mysql mysql -u weg_user -pwegsegura123 -e "USE weg_segura; SHOW TABLES;" 2>/dev/null; then
    echo "✅ Conexão testada com sucesso!"
else
    echo "⚠️  Conexão não pôde ser testada automaticamente"
fi
