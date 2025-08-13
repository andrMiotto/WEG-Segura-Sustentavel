# 📋 Changelog - Pasta Database

Todas as mudanças notáveis nesta pasta serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Versionamento Semântico](https://semver.org/lang/pt-BR/).

## [Unreleased]

### Adicionado
- Scripts de setup automatizado para Windows e Linux/Mac
- Arquivos docker-compose.yml para MySQL e InfluxDB
- Exemplos de consultas SQL e Flux
- Documentação completa de setup
- Scripts de configuração cross-platform

## [1.0.0] - 2024-12-XX

### Adicionado
- **Estrutura completa de bancos de dados**
  - MySQL 8.0 com schema completo e reduzido
  - InfluxDB 2.0 para séries temporais
  - phpMyAdmin para interface web do MySQL

- **Scripts de setup automatizado**
  - `setup-all.sh` - Setup completo para Linux/Mac
  - `setup-all.bat` - Setup completo para Windows
  - `MySQL/setup-mysql.sh` - Setup MySQL para Linux/Mac
  - `MySQL/setup-mysql.bat` - Setup MySQL para Windows
  - `InfluxDB/setup-influxdb.sh` - Setup InfluxDB para Linux/Mac
  - `InfluxDB/setup-influxdb.bat` - Setup InfluxDB para Windows

- **Configurações Docker**
  - `docker-compose.yml` - Setup completo (MySQL + InfluxDB + phpMyAdmin)
  - `MySQL/docker-compose.yml` - Setup apenas MySQL
  - `InfluxDB/docker-compose.yml` - Setup apenas InfluxDB

- **Exemplos e templates**
  - `MySQL/queries-examples.sql` - Consultas SQL de exemplo
  - `InfluxDB/queries-examples.flux` - Consultas Flux de exemplo
  - `README-setup.md` - Guia completo de setup
  - `README_database.md` - Documentação técnica detalhada

- **Arquivos de banco**
  - `MySQL/database_v1.sql` - Schema completo do banco
  - `MySQL/database_reduzido.sql` - Schema simplificado para testes
  - `MySQL/diagrama.mwb` - Modelo do banco (MySQL Workbench)

### Modificado
- **README_database.md**
  - Adicionada seção de ferramentas de banco de dados
  - Incluídas instruções de setup automatizado
  - Atualizada estrutura do projeto
  - Adicionadas informações sobre Docker e scripts

### Removido
- Nenhuma funcionalidade removida

## [0.9.0] - 2024-12-XX

### Adicionado
- **Estrutura básica de pastas**
  - Pasta `MySQL/` com arquivos de banco
  - Pasta `InfluxDB/` com classe Java
  - `README_database.md` inicial

- **Arquivos de banco MySQL**
  - `database_v1.sql` - Schema inicial
  - `database_reduzido.sql` - Versão simplificada
  - `diagrama.mwb` - Modelo do banco

- **Classes Java de teste**
  - `TesteConexaoCleverCloud.java` - Teste de conexão remota
  - `TesteInsercao.java` - Teste de inserção
  - `InfluxDBInsercao.java` - Classe para InfluxDB

### Modificado
- Nenhuma modificação

### Removido
- Nenhuma funcionalidade removida

---

## 🔄 Tipos de Mudanças

- **Adicionado** para novas funcionalidades
- **Modificado** para mudanças em funcionalidades existentes
- **Depreciado** para funcionalidades que serão removidas em breve
- **Removido** para funcionalidades removidas
- **Corrigido** para correções de bugs
- **Segurança** para correções de vulnerabilidades

## 📝 Formato de Versão

Este projeto usa [Versionamento Semântico](https://semver.org/lang/pt-BR/):

- **MAJOR** - Mudanças incompatíveis com versões anteriores
- **MINOR** - Adição de funcionalidades de forma compatível
- **PATCH** - Correções de bugs de forma compatível

## 🚀 Próximas Versões

### [1.1.0] - Planejado
- Adicionar scripts de backup automático
- Incluir configurações para diferentes ambientes (dev, staging, prod)
- Adicionar monitoramento de performance dos bancos
- Incluir scripts de migração de dados

### [1.2.0] - Planejado
- Adicionar suporte a outros bancos de dados (PostgreSQL, MongoDB)
- Incluir ferramentas de análise e relatórios
- Adicionar automação de testes de banco
- Incluir configurações de cluster e replicação

### [2.0.0] - Planejado
- Refatoração completa da arquitetura
- Suporte a microserviços
- Integração com ferramentas de CI/CD
- Automação completa de deployment

## 📞 Contribuição

Para contribuir com mudanças:

1. Faça suas alterações
2. Atualize este CHANGELOG.md
3. Incremente a versão apropriada
4. Documente as mudanças de forma clara
5. Teste localmente antes de submeter

## 🔗 Links Úteis

- [README_database.md](./README_database.md) - Documentação técnica
- [README-setup.md](./README-setup.md) - Guia de setup
- [MySQL/](./MySQL/) - Arquivos relacionados ao MySQL
- [InfluxDB/](./InfluxDB/) - Arquivos relacionados ao InfluxDB
