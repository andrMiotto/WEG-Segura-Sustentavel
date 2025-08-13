# Projeto Weg Segura

## Banco de Dados Relacional (MySQL)

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

## Banco de Dados de Logs (InfluxDB)

O **InfluxDB** é um **banco de dados não relacional**, orientado a **séries temporais**, projetado para armazenar **grandes volumes de dados de sensores e logs** com alta performance. Ele será usado como **registro de logs dos sensores IoT**.

### Acesso InfluxDB

- **URL:** `http://localhost:8086`
- **Organização:** `WegSegura`
- **Bucket:** `WegSegura`
- **Token (All Access):** `nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==`

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