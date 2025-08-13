# 🚀 Setup dos Bancos de Dados - WEG Segura

Este documento explica como configurar e executar todos os bancos de dados necessários para o projeto WEG Segura.

## 📋 Pré-requisitos

- **Docker** instalado e rodando
- **Docker Compose** instalado
- **Git** para clonar o repositório

## 🐳 Opções de Setup

### Opção 1: Setup Completo (Recomendado)
Executa todos os serviços de uma vez:

```bash
# Linux/Mac
./setup-all.sh

# Windows
setup-all.bat
```

### Opção 2: Setup Individual
Configure cada banco separadamente:

#### MySQL apenas:
```bash
# Linux/Mac
cd MySQL
./setup-mysql.sh

# Windows
cd MySQL
setup-mysql.bat
```

#### InfluxDB apenas:
```bash
# Linux/Mac
cd InfluxDB
./setup-influxdb.sh

# Windows
cd InfluxDB
setup-influxdb.bat
```

## 🗄️ Serviços Disponíveis

### 1. MySQL 8.0
- **Porta:** 3306
- **Usuário:** `weg_user`
- **Senha:** `wegsegura123`
- **Banco:** `weg_segura`
- **Interface Web:** phpMyAdmin em http://localhost:8080

### 2. InfluxDB 2.0
- **Porta:** 8086
- **Usuário:** `admin`
- **Senha:** `wegsegura123`
- **Organização:** `WegSegura`
- **Bucket:** `WegSegura`

### 3. phpMyAdmin
- **Porta:** 8080
- **Acesso:** http://localhost:8080
- **Host:** `mysql` (interno)
- **Usuário:** `weg_user`
- **Senha:** `wegsegura123`

## 🔧 Comandos Docker Úteis

```bash
# Iniciar todos os serviços
docker-compose up -d

# Parar todos os serviços
docker-compose down

# Ver logs em tempo real
docker-compose logs -f

# Ver logs de um serviço específico
docker-compose logs -f mysql
docker-compose logs -f influxdb

# Reiniciar um serviço
docker-compose restart mysql

# Ver status dos containers
docker-compose ps

# Executar comando em um container
docker exec -it weg-segura-mysql mysql -u weg_user -p weg_segura
```

## 📁 Estrutura dos Arquivos

```
database/
├── docker-compose.yml          # Setup completo (MySQL + InfluxDB + phpMyAdmin)
├── setup-all.sh               # Script Linux/Mac para setup completo
├── setup-all.bat              # Script Windows para setup completo
├── README-setup.md            # Este arquivo
├── README_database.md         # Documentação técnica
├── MySQL/
│   ├── docker-compose.yml     # Setup apenas MySQL
│   ├── setup-mysql.sh         # Script Linux/Mac para MySQL
│   ├── setup-mysql.bat        # Script Windows para MySQL
│   ├── queries-examples.sql   # Exemplos de consultas SQL
│   ├── database_v1.sql        # Schema completo
│   ├── database_reduzido.sql  # Schema simplificado
│   └── diagrama.mwb           # Modelo do banco (MySQL Workbench)
└── InfluxDB/
    ├── docker-compose.yml     # Setup apenas InfluxDB
    ├── setup-influxdb.sh      # Script Linux/Mac para InfluxDB
    ├── setup-influxdb.bat     # Script Windows para InfluxDB
    ├── queries-examples.flux  # Exemplos de consultas Flux
    └── InfluxDBInsercao.java  # Classe Java para inserção
```

## 🚨 Solução de Problemas

### Erro: Porta já em uso
```bash
# Verificar o que está usando a porta
netstat -tulpn | grep :3306
netstat -tulpn | grep :8086

# Parar o serviço conflitante ou mudar a porta no docker-compose.yml
```

### Erro: Permissão negada
```bash
# Dar permissão de execução aos scripts
chmod +x *.sh
chmod +x MySQL/*.sh
chmod +x InfluxDB/*.sh
```

### Erro: Container não inicia
```bash
# Ver logs detalhados
docker-compose logs

# Remover volumes e recriar
docker-compose down -v
docker-compose up -d
```

### Erro: Conexão recusada
```bash
# Aguardar mais tempo para inicialização
# MySQL pode levar até 1 minuto para estar pronto
# InfluxDB pode levar até 30 segundos
```

## 🔒 Segurança

- **Senhas padrão:** Todas as senhas são `wegsegura123` para desenvolvimento
- **Produção:** Altere todas as senhas antes de usar em produção
- **Redes:** Todos os serviços estão na rede interna `weg-segura-network`
- **Portas:** Apenas as portas necessárias estão expostas

## 📊 Monitoramento

### Verificar Status dos Serviços
```bash
# Status geral
docker-compose ps

# Recursos utilizados
docker stats

# Logs em tempo real
docker-compose logs -f
```

### Testar Conexões
```bash
# MySQL
mysql -h localhost -P 3306 -u weg_user -p weg_segura

# InfluxDB
curl http://localhost:8086/health
```

## 🤝 Contribuição

Para adicionar novos serviços ou modificar configurações:

1. Edite o `docker-compose.yml` apropriado
2. Atualize os scripts de setup correspondentes
3. Teste localmente
4. Documente as mudanças neste README

## 📞 Suporte

- **Issues:** Abra uma issue no GitHub
- **Logs:** Use `docker-compose logs` para debug
- **Documentação:** Consulte `README_database.md` para detalhes técnicos
