#!/bin/bash

echo "🚀 Configurando InfluxDB para WEG Segura..."

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Inicie o Docker primeiro."
    exit 1
fi

# Para e remove container existente se houver
echo "🔄 Parando container existente..."
docker stop weg-segura-influxdb 2>/dev/null || true
docker rm weg-segura-influxdb 2>/dev/null || true

# Inicia o InfluxDB
echo "🐳 Iniciando InfluxDB..."
docker-compose up -d

# Aguarda o InfluxDB estar pronto
echo "⏳ Aguardando InfluxDB inicializar..."
sleep 10

# Verifica se está rodando
if docker ps | grep -q "weg-segura-influxdb"; then
    echo "✅ InfluxDB iniciado com sucesso!"
    echo ""
    echo "📊 Informações de acesso:"
    echo "   URL: http://localhost:8086"
    echo "   Usuário: admin"
    echo "   Senha: wegsegura123"
    echo "   Organização: WegSegura"
    echo "   Bucket: WegSegura"
    echo "   Token: nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g=="
    echo ""
    echo "🔗 Acesse http://localhost:8086 para configurar via interface web"
else
    echo "❌ Erro ao iniciar InfluxDB"
    exit 1
fi
