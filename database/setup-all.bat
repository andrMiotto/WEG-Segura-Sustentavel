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

REM Para e remove containers existentes se houver
echo 🔄 Parando containers existentes...
docker stop weg-segura-mysql weg-segura-influxdb weg-segura-phpmyadmin >nul 2>&1
docker rm weg-segura-mysql weg-segura-influxdb weg-segura-phpmyadmin >nul 2>&1

REM Inicia todos os serviços
echo 🐳 Iniciando todos os serviços...
docker-compose up -d

REM Aguarda os serviços estarem prontos
echo ⏳ Aguardando serviços inicializarem...
echo    MySQL: 30 segundos...
timeout /t 30 /nobreak >nul
echo    InfluxDB: 10 segundos...
timeout /t 10 /nobreak >nul

REM Verifica se todos estão rodando
echo 🔍 Verificando status dos serviços...

set mysql_running=false
set influxdb_running=false
set phpmyadmin_running=false

docker ps | findstr "weg-segura-mysql" >nul
if %errorlevel% equ 0 (
    set mysql_running=true
    echo ✅ MySQL: Rodando
) else (
    echo ❌ MySQL: Parado
)

docker ps | findstr "weg-segura-influxdb" >nul
if %errorlevel% equ 0 (
    set influxdb_running=true
    echo ✅ InfluxDB: Rodando
) else (
    echo ❌ InfluxDB: Parado
)

docker ps | findstr "weg-segura-phpmyadmin" >nul
if %errorlevel% equ 0 (
    set phpmyadmin_running=true
    echo ✅ phpMyAdmin: Rodando
) else (
    echo ❌ phpMyAdmin: Parado
)

echo.
echo 📊 RESUMO DA CONFIGURAÇÃO
echo =========================

if "%mysql_running%"=="true" (
    echo 🗄️  MySQL:
    echo    Host: localhost
    echo    Porta: 3306
    echo    Usuário: weg_user
    echo    Senha: wegsegura123
    echo    Banco: weg_segura
    echo    phpMyAdmin: http://localhost:8080
    echo.
)

if "%influxdb_running%"=="true" (
    echo 📈 InfluxDB:
    echo    URL: http://localhost:8086
    echo    Usuário: admin
    echo    Senha: wegsegura123
    echo    Organização: WegSegura
    echo    Bucket: WegSegura
    echo.
)

echo 🔗 COMANDOS ÚTEIS:
echo    Parar todos: docker-compose down
echo    Ver logs: docker-compose logs -f
echo    Reiniciar: docker-compose restart
echo.

if "%mysql_running%"=="true" if "%influxdb_running%"=="true" (
    echo 🎉 Todos os serviços estão rodando com sucesso!
    echo    Acesse http://localhost:8080 para gerenciar o MySQL
    echo    Acesse http://localhost:8086 para gerenciar o InfluxDB
) else (
    echo ⚠️  Alguns serviços não estão rodando. Verifique os logs:
    echo    docker-compose logs
)

pause
