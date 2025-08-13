#!/bin/bash

echo "🚀 Configurando TODOS os bancos de dados para WEG Segura..."
echo "=================================================="

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Inicie o Docker primeiro."
    exit 1
fi

# Para e remove containers existentes se houver
echo "🔄 Parando containers existentes..."
docker stop weg-segura-mysql weg-segura-influxdb weg-segura-phpmyadmin 2>/dev/null || true
docker rm weg-segura-mysql weg-segura-influxdb weg-segura-phpmyadmin 2>/dev/null || true

# Inicia todos os serviços
echo "🐳 Iniciando todos os serviços..."
docker-compose up -d

# Aguarda os serviços estarem prontos
echo "⏳ Aguardando serviços inicializarem..."
echo "   MySQL: 30 segundos..."
sleep 30
echo "   InfluxDB: 10 segundos..."
sleep 10

# Verifica se todos estão rodando
echo "🔍 Verificando status dos serviços..."

mysql_running=false
influxdb_running=false
phpmyadmin_running=false

if docker ps | grep -q "weg-segura-mysql"; then
    mysql_running=true
    echo "✅ MySQL: Rodando"
else
    echo "❌ MySQL: Parado"
fi

if docker ps | grep -q "weg-segura-influxdb"; then
    influxdb_running=true
    echo "✅ InfluxDB: Rodando"
else
    echo "❌ InfluxDB: Parado"
fi

if docker ps | grep -q "weg-segura-phpmyadmin"; then
    phpmyadmin_running=true
    echo "✅ phpMyAdmin: Rodando"
else
    echo "❌ phpMyAdmin: Parado"
fi

echo ""
echo "📊 RESUMO DA CONFIGURAÇÃO"
echo "========================="

if [ "$mysql_running" = true ]; then
    echo "🗄️  MySQL:"
    echo "   Host: localhost"
    echo "   Porta: 3306"
    echo "   Usuário: weg_user"
    echo "   Senha: wegsegura123"
    echo "   Banco: weg_segura"
    echo "   phpMyAdmin: http://localhost:8080"
    echo ""
fi

if [ "$influxdb_running" = true ]; then
    echo "📈 InfluxDB:"
    echo "   URL: http://localhost:8086"
    echo "   Usuário: admin"
    echo "   Senha: wegsegura123"
    echo "   Organização: WegSegura"
    echo "   Bucket: WegSegura"
    echo ""
fi

echo "🔗 COMANDOS ÚTEIS:"
echo "   Parar todos: docker-compose down"
echo "   Ver logs: docker-compose logs -f"
echo "   Reiniciar: docker-compose restart"
echo ""

if [ "$mysql_running" = true ] && [ "$influxdb_running" = true ]; then
    echo "🎉 Todos os serviços estão rodando com sucesso!"
    echo "   Acesse http://localhost:8080 para gerenciar o MySQL"
    echo "   Acesse http://localhost:8086 para gerenciar o InfluxDB"
else
    echo "⚠️  Alguns serviços não estão rodando. Verifique os logs:"
    echo "   docker-compose logs"
fi
