#!/bin/bash
echo "🚀 Configurando TODOS os bancos de dados para WEG Segura..."
echo "=================================================="

# Verifica se o Docker está rodando
if ! docker info >/dev/null 2>&1; then
    echo "❌ Docker não está rodando. Inicie o Docker primeiro."
    exit 1
fi

# Para e remove container do InfluxDB (MySQL é na Clever Cloud, não roda local)
echo "🔄 Parando InfluxDB local existente..."
docker stop weg-segura-influxdb >/dev/null 2>&1
docker rm weg-segura-influxdb >/dev/null 2>&1

# Inicia InfluxDB local
echo "🐳 Iniciando InfluxDB..."
docker-compose up -d weg-segura-influxdb

# Aguarda inicialização
echo "⏳ Aguardando InfluxDB inicializar..."
sleep 10

# Verifica status
echo "🔍 Verificando status do InfluxDB..."

influxdb_running=false

if docker ps | grep -q "weg-segura-influxdb"; then
    influxdb_running=true
    echo "✅ InfluxDB: Rodando"
else
    echo "❌ InfluxDB: Parado"
fi

echo
echo "📊 RESUMO DA CONFIGURAÇÃO"
echo "========================="

echo "🗄️  MySQL (Clever Cloud):"
echo "   Host: bmjbvsmlzkvrphhok83p-mysql.services.clever-cloud.com"
echo "   Porta: 3306"
echo "   Usuario: u0np3s8gbvzfctph"
echo "   Senha: zXUOwzICMsDyvmzTVVqV"
echo "   Banco: bmjbvsmlzkvrphhok83p"
echo "   Conexao: Weg Segura"
echo

if [ "$influxdb_running" = true ]; then
    echo "📈 InfluxDB:"
    echo "   URL: http://192.168.56.1:8086"
    echo "   Organizacao: Weg"
    echo "   Bucket: WegSegura"
    echo "   Token: EnMZnSwm08sptanEmlbWavHASmtDEmYHepuRJzezGARphMo6kM1vMGF_SyLbq1VFSNPs8G13BDyXIXkXpOYE1A=="
    echo
fi

echo "🔗 COMANDOS ÚTEIS:"
echo "   Parar todos: docker-compose down"
echo "   Ver logs: docker-compose logs -f"
echo "   Reiniciar: docker-compose restart"
echo

if [ "$influxdb_running" = true ]; then
    echo "🎉 InfluxDB rodando local e MySQL configurado na Clever Cloud!"
    echo "   MySQL: bmjbvsmlzkvrphhok83p-mysql.services.clever-cloud.com:3306"
    echo "   InfluxDB: http://192.168.56.1:8086"
else
    echo "⚠️  InfluxDB nao iniciou. Verifique os logs:"
    echo "   docker-compose logs"
fi
