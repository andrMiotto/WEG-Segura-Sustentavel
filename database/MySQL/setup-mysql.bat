@echo off
echo 🚀 Configurando MySQL para WEG Segura...

REM Verifica se o Docker está rodando
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker não está rodando. Inicie o Docker primeiro.
    pause
    exit /b 1
)

REM Para e remove container existente se houver
echo 🔄 Parando container existente...
docker stop weg-segura-mysql >nul 2>&1
docker rm weg-segura-mysql >nul 2>&1

REM Inicia o MySQL
echo 🐳 Iniciando MySQL...
docker-compose up -d

REM Aguarda o MySQL estar pronto
echo ⏳ Aguardando MySQL inicializar...
timeout /t 30 /nobreak >nul

REM Verifica se está rodando
docker ps | findstr "weg-segura-mysql" >nul
if %errorlevel% equ 0 (
    echo ✅ MySQL iniciado com sucesso!
    echo.
    echo 📊 Informações de acesso:
    echo    Host: localhost
    echo    Porta: 3306
    echo    Usuário root: root
    echo    Senha root: wegsegura123
    echo    Usuário: weg_user
    echo    Senha: wegsegura123
    echo    Banco: weg_segura
    echo.
    echo 🔗 Para conectar via linha de comando:
    echo    mysql -h localhost -P 3306 -u weg_user -p weg_segura
    echo.
    echo 📁 Scripts SQL carregados automaticamente:
    echo    - database_v1.sql (schema completo)
    echo    - database_reduzido.sql (schema simplificado)
) else (
    echo ❌ Erro ao iniciar MySQL
)

REM Testa conexão
echo 🧪 Testando conexão...
docker exec weg-segura-mysql mysql -u weg_user -pwegsegura123 -e "USE weg_segura; SHOW TABLES;" >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Conexão testada com sucesso!
) else (
    echo ⚠️  Conexão não pôde ser testada automaticamente
)

pause
