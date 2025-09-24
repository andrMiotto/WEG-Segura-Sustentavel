@echo off
echo 🚀 Configurando TODOS os bancos de dados para WEG Segura...
echo ==================================================

REM Verifica se o Docker está rodando
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker não está rodando. Inicie o Docker primeiro.
    pause
    exit /b 1
)

REM Verifica se o container InfluxDB já existe
docker ps -a --format "{{.Names}}" | findstr /i "weg-segura-influxdb" >nul
if %errorlevel% equ 0 (
    echo 🔄 Container InfluxDB já existe. Reiniciando...
    docker start weg-segura-influxdb >nul 2>&1
) else (
    echo 🐳 Criando novo container InfluxDB...
    docker-compose up -d weg-segura-influxdb
)

REM Aguarda serviços estarem prontos
echo ⏳ Aguardando inicializacao do InfluxDB...
timeout /t 10 /nobreak >nul

REM Verifica se InfluxDB está rodando
echo 🔍 Verificando status dos serviços...

set influxdb_running=false

docker ps | findstr "weg-segura-influxdb" >nul
if %errorlevel% equ 0 (
    set influxdb_running=true
    echo ✅ InfluxDB: Rodando
) else (
    echo ❌ InfluxDB: Parado
)

echo.
echo 📊 RESUMO DA CONFIGURAÇÃO
echo =========================

echo 🗄️  MySQL (Clever Cloud):
echo    Host: bmjbvsmlzkvrphhok83p-mysql.services.clever-cloud.com
echo    Porta: 3306
echo    Usuario: u0np3s8gbvzfctph
echo    Senha: zXUOwzICMsDyvmzTVVqV
echo    Banco: bmjbvsmlzkvrphhok83p
echo    Conexao: Weg Segura
echo.

if "%influxdb_running%"=="true" (
    echo 📈 InfluxDB:
    echo    URL: http://192.168.56.1:8086
    echo    Organizacao: Weg
    echo    Bucket: WegSegura
    echo    Token: (verifique no painel do Influx em Settings > API Tokens)
    echo.
)

echo 🔗 COMANDOS ÚTEIS:
echo    Parar todos: docker-compose down
echo    Ver logs: docker-compose logs -f
echo    Reiniciar: docker-compose restart
echo.

if "%influxdb_running%"=="true" (
    echo 🎉 InfluxDB rodando local e MySQL configurado na Clever Cloud!
    echo    MySQL: bmjbvsmlzkvrphhok83p-mysql.services.clever-cloud.com:3306
    echo    InfluxDB: http://192.168.56.1:8086
) else (
    echo ⚠️  InfluxDB nao iniciou. Verifique os logs:
    echo    docker-compose logs weg-segura-influxdb
)

pause
