@echo off
echo 🚀 Configurando InfluxDB para WEG Segura...

REM Verifica se o Docker está rodando
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker não está rodando. Inicie o Docker primeiro.
    pause
    exit /b 1
)

REM Para e remove container existente se houver
echo 🔄 Parando container existente...
docker stop weg-segura-influxdb >nul 2>&1
docker rm weg-segura-influxdb >nul 2>&1

REM Inicia o InfluxDB
echo 🐳 Iniciando InfluxDB...
docker-compose up -d

REM Aguarda o InfluxDB estar pronto
echo ⏳ Aguardando InfluxDB inicializar...
timeout /t 10 /nobreak >nul

REM Verifica se está rodando
docker ps | findstr "weg-segura-influxdb" >nul
if %errorlevel% equ 0 (
    echo ✅ InfluxDB iniciado com sucesso!
    echo.
    echo 📊 Informações de acesso:
    echo    URL: http://localhost:8086
    echo    Usuário: admin
    echo    Senha: wegsegura123
    echo    Organização: WegSegura
    echo    Bucket: WegSegura
    echo    Token: nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==
    echo.
    echo 🔗 Acesse http://localhost:8086 para configurar via interface web
) else (
    echo ❌ Erro ao iniciar InfluxDB
)

pause
