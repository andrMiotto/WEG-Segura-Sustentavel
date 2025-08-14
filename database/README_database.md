# Projeto Weg Segura - Documentação do Banco de Dados

## 📋 Visão Geral do Projeto

O **WEG Segura Sustentável** é um sistema de segurança inteligente que monitora emergências em tempo real, utilizando sensores IoT para detectar movimentação em salas e rastrear pessoas durante situações de risco. O sistema integra bancos de dados relacionais (MySQL) e de séries temporais (InfluxDB) para fornecer uma solução completa de monitoramento e resposta a emergências.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 22
- **Build Tool:** Maven 3.11.0
- **Banco Relacional:** MySQL 8.0
- **Banco de Séries Temporais:** InfluxDB 6.11.0
- **Hospedagem:** Clever Cloud (MySQL) e InfluxDB Cloud (AWS)

## 📁 Estrutura do Projeto

```
WEG-Segura-Sustentavel/
├── 📁 database/
│   ├── 📁 MySQL/
│   │   ├── database_v1.sql          # Schema completo do banco
│   │   ├── database_reduzido.sql    # Schema simplificado para testes
│   │   ├── diagrama.mwb             # Diagrama do banco (MySQL Workbench)
│   │   ├── TesteConexaoCleverCloud.java  # Teste de conexão remota
│   │   ├── TesteInsercao.java       # Teste de inserção de dados
│   │   ├── docker-compose.yml       # Setup MySQL local com Docker
│   │   ├── setup-mysql.sh           # Script Linux/Mac para MySQL
│   │   ├── setup-mysql.bat          # Script Windows para MySQL
│   │   └── queries-examples.sql     # Exemplos de consultas SQL
│   ├── 📁 InfluxDB/
│   │   ├── InfluxDBInsercao.java    # Classe para inserção de logs
│   │   ├── docker-compose.yml       # Setup InfluxDB local com Docker
│   │   ├── setup-influxdb.sh        # Script Linux/Mac para InfluxDB
│   │   ├── setup-influxdb.bat       # Script Windows para InfluxDB
│   │   └── queries-examples.flux    # Exemplos de consultas Flux
│   ├── docker-compose.yml            # Setup completo (MySQL + InfluxDB + phpMyAdmin)
│   ├── setup-all.sh                 # Script Linux/Mac para setup completo
│   ├── setup-all.bat                # Script Windows para setup completo
│   ├── README-setup.md              # Guia de setup dos bancos
│   ├── CHANGELOG.md                 # Histórico de mudanças e versões
│   └── README_database.md            # Este arquivo (documentação técnica)
├── 📁 src/
│   └── 📁 main/
│       ├── 📁 java/
│       │   └── 📁 org/weg/
│       │       └── Main.java         # Classe principal da aplicação
│       └── 📁 resources/
│           └── 📁 templates/
│               ├── index.html        # Interface web principal
│               └── style.css         # Estilos da interface
├── pom.xml                           # Configuração Maven
└── README.md                         # Documentação geral do projeto
```

## 🗄️ Banco de Dados Relacional (MySQL)

O banco de dados relacional principal é **MySQL**, utilizado para armazenar informações estruturadas sobre **emergências, salas e pessoas**. Ele está hospedado na plataforma **Clever Cloud**, permitindo acesso remoto seguro para desenvolvimento, testes e integração com sistemas externos.

### Dicionário de Dados

| Tabela            | Atributo              | Tipo                      | Restrições                                 | Descrição                                                      |
|-------------------|----------------------|---------------------------|--------------------------------------------|----------------------------------------------------------------|
| **emergencias**   | `id`                  | INT                       | PK, AUTO_INCREMENT                         | Identificador único da emergência                              |
|                   | `titulo`              | VARCHAR(100)              | NOT NULL                                   | Título descritivo da emergência                               |
|                   | `descricao`           | TEXT                      | NULLABLE                                   | Texto explicativo detalhando a emergência                      |
|                   | `inicio`              | DATETIME                  | DEFAULT CURRENT_TIMESTAMP                   | Data e hora de início da emergência                            |
|                   | `fim`                 | DATETIME                  | DEFAULT NULL                               | Data e hora de encerramento da emergência                      |
|                   | `em_andamento`        | BOOLEAN                   | NOT NULL DEFAULT FALSE                      | Flag para indicar se a emergência está ativa                   |
| **salas**         | `id`                  | INT                       | PK, AUTO_INCREMENT                         | Identificador único da sala                                    |
|                   | `numero`              | INT                       | NOT NULL                                   | Número da sala                                                |
|                   | `bloco`               | VARCHAR(50)               | NOT NULL                                   | Bloco/setor da sala                                          |
|                   | `portaria`            | INT                       | NOT NULL                                   | Número da portaria associada                                  |
|                   | `unidade`             | VARCHAR(50)               | NOT NULL                                   | Unidade administrativa/organização da sala                    |
|                   | `situacao_de_risco`   | BOOLEAN                   | NOT NULL DEFAULT FALSE                      | Indica se a sala está em situação de risco                    |
|                   | `id_emergencia_atual` | INT                       | FK `emergencias(id)`, DEFAULT NULL         | Emergência atualmente associada à sala                        |
| **pessoas**       | `id`                  | INT                       | PK, AUTO_INCREMENT                         | Identificador único da pessoa                                 |
|                   | `nome`                | VARCHAR(100)              | NOT NULL                                   | Nome completo da pessoa                                       |
|                   | `cadastro`            | INT                       | NOT NULL, UNIQUE                           | Número único de cadastro (ex: matrícula)                      |
|                   | `tipo`                | ENUM                      | NOT NULL DEFAULT 'Outro'                    | Tipo da pessoa (Colaborador, Visitante, Terceiro, etc.)       |
|                   | `situacao_de_risco`   | BOOLEAN                   | NOT NULL DEFAULT FALSE                      | Indica se a pessoa está em situação de risco                  |
|                   | `id_sala_atual`       | INT                       | FK `salas(id)`, NOT NULL                    | Sala atual onde a pessoa está alocada                         |
|                   | `id_emergencia_atual` | INT                       | FK `emergencias(id)`, DEFAULT NULL         | Emergência associada à pessoa                                 |
| **salas_emergencias** | `id`               | INT                       | PK, AUTO_INCREMENT                         | Identificador único da relação entre sala e emergência       |
|                   | `sala_id`             | INT                       | FK `salas(id)`, NOT NULL                    | Sala associada                                               |
|                   | `emergencia_id`       | INT                       | FK `emergencias(id)`, NOT NULL              | Emergência associada                                         |

### Views, Triggers e Stored Procedures

O banco possui **views, triggers e stored procedures** para facilitar o monitoramento e atualização automática de status de pessoas e salas durante emergências.

- **Views**: ex. `view_pessoas_em_emergencias`, `view_pessoa_situacao` — mostram relação entre pessoas, salas e emergências ativas.
- **Trigger `trg_finalizar_emergencia`**: atualiza pessoas e salas ao encerrar uma emergência.
- **Stored Procedures**: `registrar_emergencia`, `encerrar_emergencia`, `associar_pessoa_emergencia`, `associar_sala_emergencia`.

### Credenciais de Conexão MySQL

| Parâmetro        | Valor                                                  |
|------------------|--------------------------------------------------------|
| Hostname         | `bmjbvsmlzkvrphhok83p-mysql.services.clever-cloud.com` |
| Porta            | `3306`                                                 |
| Usuário          | `u0np3s8gbvzfctph`                                     |
| Senha            | `zXUOwzICMsDyvmzTVVqV`                                 |
| Nome do Banco    | `bmjbvsmlzkvrphhok83p`                                 |
| Nome da Conexão  | `Weg Segura`                                           |

---

## 📊 Banco de Dados de Logs (InfluxDB)

O **InfluxDB** é um **banco de dados não relacional**, orientado a **séries temporais**, projetado para armazenar **grandes volumes de dados de sensores e logs** com alta performance. Ele será usado como **registro de logs dos sensores IoT**.

---

### 🔹 Acesso InfluxDB

#### InfluxDB Local (Desenvolvimento)

- **URL (acesso local):** `http://localhost:8086`  
- **Organização:** `WegSegura`  
- **Bucket:** `WegSegura`  
- **Token (All Access):** nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==

##### 🌐 **Acessando de outro PC na mesma rede local**
Caso o InfluxDB esteja rodando via Docker ou instalado localmente, é possível acessar de outro computador substituindo `localhost` pelo **IP local** da máquina onde o serviço está sendo executado, que é **http://192.168.56.1:8086**.

#### InfluxDB Cloud (Produção)
URL: https://us-east-1-1.aws.cloud2.influxdata.com

Acesso Web (via túnel local):

```bash
http://localhost:8086/signin?returnTo=/orgs/03c7ab261562918c/data-explorer

Usuário: admin

Senha: admin123

Organização: WegSegura

Bucket: WegSegura

Storage Provider: AWS

Token: Configurado via variáveis de ambiente
```

### Sintaxe de Inserção de Dados

Cada ponto no InfluxDB possui:

- **Measurement** → nome do conjunto de dados (ex: `logs_sensores`)
- **Tags** → metadados indexados (ex: `sala`, `pessoa`)
- **Fields** → valores reais do dado (ex: `ha_movimento_na_sala`)
- **Timestamp** → instante de registro

Exemplo em Java:

```java
Point ponto1 = Point.measurement("logs_sensores")
        .addTag("sala", "1")
        .addTag("pessoa", "10")
        .addField("ha_movimento_na_sala", true)
        .time(System.currentTimeMillis(), WritePrecision.MS);
```

### Exemplo de Consulta no InfluxDB

As consultas no InfluxDB são feitas utilizando a **linguagem Flux**, que permite filtrar e processar séries temporais de forma eficiente.  

#### Exemplo 1: Última hora de dados dos sensores

```flux
from(bucket: "WegSegura")
  |> range(start: -1h)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
```

#### Exemplo 2: Dados de uma sala específica

```flux
from(bucket: "WegSegura")
  |> range(start: -24h)
  |> filter(fn: (r) => r._measurement == "logs_sensores" and r.sala == "1")
  |> aggregateWindow(every: 5m, fn: mean)
```

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 22 ou superior
- Maven 3.11.0 ou superior
- MySQL 8.0 (local ou remoto)
- InfluxDB 2.x (local)

### Configuração

#### Opção 1: Setup Automatizado com Docker (Recomendado)

1. **Clone o repositório:**
   ```bash
   git clone [URL_DO_REPOSITORIO]
   cd WEG-Segura-Sustentavel
   cd database
   ```

2. **Execute o setup completo:**
   ```bash
   # Linux/Mac
   chmod +x *.sh MySQL/*.sh InfluxDB/*.sh
   ./setup-all.sh
   
   # Windows
   setup-all.bat
   ```

3. **Acesse os serviços:**
   - **phpMyAdmin (MySQL):** http://localhost:8080
   - **InfluxDB:** http://localhost:8086
   - **MySQL CLI:** `mysql -h localhost -P 3306 -u weg_user -p weg_segura`

#### Opção 2: Setup Manual

1. **Clone o repositório:**
   ```bash
   git clone [URL_DO_REPOSITORIO]
   cd WEG-Segura-Sustentavel
   ```

2. **Configure o banco MySQL:**
   - Execute o script `database/MySQL/database_v1.sql` no seu MySQL
   - Ou use o banco remoto na Clever Cloud (credenciais acima)
   - **MySQL Workbench**: Abra o arquivo `database/MySQL/diagrama.mwb` para visualizar o modelo do banco
   - **DBeaver**: Conecte-se ao banco para consultas e gerenciamento

3. **Configure o InfluxDB:**
   - **Local (Docker)**: Execute `docker run -d -p 8086:8086 influxdb:2.0` para InfluxDB v2
   - **Cloud**: Use o InfluxDB Cloud hospedado na AWS
   - Crie a organização `WegSegura` e o bucket `WegSegura`
   - Configure o token de acesso
   - **DBeaver**: Conecte-se ao InfluxDB para visualização dos dados

4. **Compile o projeto:**
   ```bash
   mvn clean compile
   ```

5. **Execute os testes:**
   ```bash
   # Teste de conexão MySQL
   mvn exec:java -Dexec.mainClass="database.MySQL.TesteConexaoCleverCloud"
   
   # Teste de inserção MySQL
   mvn exec:java -Dexec.mainClass="database.MySQL.TesteInsercao"
   
   # Teste de inserção InfluxDB
   mvn exec:java -Dexec.mainClass="database.InfluxDB.InfluxDBInsercao"
   ```

## 🔧 Desenvolvimento

### Estrutura de Classes Java

- **`TesteConexaoCleverCloud.java`**: Testa conexão com MySQL remoto
- **`TesteInsercao.java`**: Testa inserção de dados no MySQL
- **`InfluxDBInsercao.java`**: Gerencia inserção de logs no InfluxDB

### Dependências Maven

- **`mysql-connector-java:8.0.33`**: Driver MySQL para Java
- **`influxdb-client-java:6.11.0`**: Cliente oficial do InfluxDB

### 🛠️ Ferramentas de Desenvolvimento

- **MySQL Workbench**: Para modelagem e design do banco de dados
- **DBeaver**: Cliente universal para consultas e administração de ambos os bancos
- **Docker**: Para execução local do InfluxDB v2 em container
- **InfluxDB Cloud**: Para hospedagem em produção na AWS
- **Maven**: Para build e gerenciamento de dependências

## 📚 Documentação Adicional

- **[README-setup.md](./README-setup.md)**: Guia completo de setup dos bancos de dados
- **[CHANGELOG.md](./CHANGELOG.md)**: Histórico de mudanças e versões
- **[MySQL/queries-examples.sql](./MySQL/queries-examples.sql)**: Exemplos de consultas SQL
- **[InfluxDB/queries-examples.flux](./InfluxDB/queries-examples.flux)**: Exemplos de consultas Flux
