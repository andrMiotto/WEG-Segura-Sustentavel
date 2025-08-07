# Dicionário de Dados - Banco de Dados `weg_seguranca`

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

# Explicação das Views

### view_pessoas_em_emergencias  
Lista pessoas que estão associadas a uma emergência ativa, mostrando seus dados básicos e o status da emergência.

### view_pessoas_por_sala  
Exibe a relação entre pessoas e a sala onde elas estão alocadas, com informações de identificação e tipo.

### view_pessoa_situacao  
Combina informações da pessoa, sua sala atual, e a emergência em andamento (se houver), incluindo status de risco.

### view_emergencias_pessoas  
Relaciona emergências com as pessoas afetadas, mostrando dados da emergência e das pessoas.

### view_emergencia_salas  
Exibe as emergências que estão em andamento e as salas associadas a elas, com status de risco da sala.


# Explicação da Trigger

### trg_finalizar_emergencia  
Executada após atualização na tabela `emergencias`. Quando uma emergência muda de `em_andamento = TRUE` para `FALSE`, essa trigger:  
- Atualiza todas as pessoas associadas para `situacao_de_risco = FALSE` e limpa o vínculo com a emergência atual (`id_emergencia_atual = NULL`).  
- Atualiza todas as salas afetadas, removendo o status de risco e desvinculando da emergência.

  
# Explicação das Stored Procedures (SP)

### registrar_emergencia(p_titulo, p_descricao)  
Cria uma nova emergência, inserindo o título, descrição e setando o status como `em_andamento = TRUE`.

### encerrar_emergencia(emergencia_id)  
Finaliza uma emergência existente, definindo o horário de término (`fim`) e mudando o status para `em_andamento = FALSE`.

### associar_pessoa_emergencia(pessoa_id, emergencia_id)  
Associa uma pessoa a uma emergência, apenas se ela estiver marcada com `situacao_de_risco = TRUE`. Atualiza o campo `id_emergencia_atual`.

### associar_sala_emergencia(sala_id, emergencia_id)  
Associa uma sala a uma emergência, marcando a sala como em situação de risco e vinculando-a à emergência. Também insere um registro na tabela de relação `salas_emergencias`.



# Explicação das Views

### view_pessoas_em_emergencias  
Lista pessoas que estão associadas a uma emergência ativa, mostrando seus dados básicos e o status da emergência.

### view_pessoas_por_sala  
Exibe a relação entre pessoas e a sala onde elas estão alocadas, com informações de identificação e tipo.

### view_pessoa_situacao  
Combina informações da pessoa, sua sala atual, e a emergência em andamento (se houver), incluindo status de risco.

### view_emergencias_pessoas  
Relaciona emergências com as pessoas afetadas, mostrando dados da emergência e das pessoas.

### view_emergencia_salas  
Exibe as emergências que estão em andamento e as salas associadas a elas, com status de risco da sala.


# Explicação da Trigger

### trg_finalizar_emergencia  
Executada após atualização na tabela `emergencias`. Quando uma emergência muda de `em_andamento = TRUE` para `FALSE`, essa trigger:  
- Atualiza todas as pessoas associadas para `situacao_de_risco = FALSE` e limpa o vínculo com a emergência atual (`id_emergencia_atual = NULL`).  
- Atualiza todas as salas afetadas, removendo o status de risco e desvinculando da emergência.

  
# Explicação das Stored Procedures (SP)

### registrar_emergencia(p_titulo, p_descricao)  
Cria uma nova emergência, inserindo o título, descrição e setando o status como `em_andamento = TRUE`.

### encerrar_emergencia(emergencia_id)  
Finaliza uma emergência existente, definindo o horário de término (`fim`) e mudando o status para `em_andamento = FALSE`.

### associar_pessoa_emergencia(pessoa_id, emergencia_id)  
Associa uma pessoa a uma emergência, apenas se ela estiver marcada com `situacao_de_risco = TRUE`. Atualiza o campo `id_emergencia_atual`.

### associar_sala_emergencia(sala_id, emergencia_id)  
Associa uma sala a uma emergência, marcando a sala como em situação de risco e vinculando-a à emergência. Também insere um registro na tabela de relação `salas_emergencias`.

# Acesso ao Banco de Dados

O banco de dados `weg_seguranca` está hospedado na plataforma **Railway**, o que permite acesso remoto para desenvolvimento, testes e integração com sistemas externos.

## 🎯 Conexão com o Banco de Dados - Railway (MySQL)

### 🌐 Acesso Externo (Público)

| Parâmetro        | Valor                                                                                   |
|------------------|-----------------------------------------------------------------------------------------|
| **Host**         | `switchyard.proxy.rlwy.net`                                                             |
| **Porta**        | `23670`                                                                                 |
| **Usuário**      | `root`                                                                                  |
| **Senha**        | `ASXuAZBXFgKPrgBhEWZvJTGCncZIxqXt`                                                      |
| **Banco**        | `railway`                                                                               |
| **URL completa** | `mysql://root:ASXuAZBXFgKPrgBhEWZvJTGCncZIxqXt@switchyard.proxy.rlwy.net:23670/railway` |

---

### 🛡️ Acesso Interno (Dentro do Railway)

| Parâmetro        | Valor                                                                               |
|------------------|-------------------------------------------------------------------------------------|
| **Host**         | `mysql.railway.internal`                                                            |
| **Porta**        | `3306`                                                                              |
| **Usuário**      | `root`                                                                              |
| **Senha**        | `ASXuAZBXFgKPrgBhEWZvJTGCncZIxqXt`                                                  |
| **Banco**        | `railway`                                                                           |
| **URL completa** | `mysql://root:ASXuAZBXFgKPrgBhEWZvJTGCncZIxqXt@mysql.railway.internal:3306/railway` |

---

### ✅ Exemplo de uso no JDBC (Java)

```java
String url = "jdbc:mysql://switchyard.proxy.rlwy.net:23670/railway";
String usuario = "root";
String senha = "ASXuAZBXFgKPrgBhEWZvJTGCncZIxqXt";

Connection conexao = DriverManager.getConnection(url, usuario, senha);
