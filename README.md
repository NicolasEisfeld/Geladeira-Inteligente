# MagicFridgeAI
![Java](https://img.shields.io/badge/Java-262626.svg??style=for-the-badge&logo=openjdk&logoColor=white)&nbsp;
![Spring Boot](https://img.shields.io/badge/Spring_Boot-262626?style=flat&logo=Spring)&nbsp;
![HTML](https://img.shields.io/badge/-HTML-262626?style=flat&logo=HTML5)&nbsp;
![CSS](https://img.shields.io/badge/CSS-262626?logo=css&logoColor=43a8d4)&nbsp;
![Flyway](https://img.shields.io/badge/-Flyway-262626?style=flat&logo=flyway)&nbsp;
![H2](https://img.shields.io/badge/-H2-262626?style=flat&logo=database)&nbsp;
![Lombok](https://img.shields.io/badge/-Lombok-262626?style=flat&logo=Lombok)&nbsp;

Sistema de geladeira inteligente desenvolvido com Java e Spring Boot, incluindo funcionalidades de gerenciamento de alimentos e sugestão de receitas baseadas em IA. 
Esse projeto permite que o Usuário insira alimentos em uma geladeira virtual alimentada por IA que sugere receitas com base nos ingredientes disponíveis.

## Tecnologias Utilizadas

- **Java 24**
- **Spring Boot 3.5.6**
- **Spring Data JPA** - Para persistência de dados
- **Spring Web** - Para desenvolvimento de APIs REST
- **Spring WebFlux** - Para programação reativa
- **H2 Database** - Banco de dados em memória
- **Flyway** - Para migrações de banco de dados
- **Lombok** - Para redução de código boilerplate
- **Jackson** - Para processamento de JSON
- **Spring Boot DevTools** - Para desenvolvimento em tempo real

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

## Como Instalar e Executar

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd MagicFridgeAI
   ```

2. **Compile o projeto:**
   ```bash
   ./mvnw clean install
   ```

3. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acesse a aplicação:**
   - Interface web: http://localhost:8080
   - Console H2: http://localhost:8080/h2-console

## Funcionalidades Principais

### Alimentos
- Cadastro: Nome, Categoria, Quantidade e Data de validade.
- Listagem: Visualização de todos os alimentos cadastrados na geladeira.
- Gerenciamento: Busca por ID, alteração e exclusão de alimentos.
- Controle: Monitoramento de validade e estoque.

### Receitas
- Sugestão: Recomendação automática de receitas baseadas nos ingredientes disponíveis.
- Listagem: Visualização de todas as receitas cadastradas.
- Gerenciamento: Busca por ID, alteração e exclusão de receitas.
- Associação: Receitas vinculadas aos ingredientes disponíveis na geladeira.

### Inteligência Artificial
- Análise: Processamento dos ingredientes disponíveis para sugerir receitas.
- Recomendação: Sistema inteligente que combina ingredientes em receitas viáveis.
- Aprendizado: Melhoria contínua das sugestões baseada no uso do sistema.

## Endpoints da API

### 1. Alimentos (/alimento)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/alimento/boasvindas` | Retorna mensagem de boas-vindas |
| POST | `/alimento/adicionar` | Cadastra um novo alimento |
| GET | `/alimento/listar` | Lista todos os alimentos cadastrados |
| GET | `/alimento/listar/{id}` | Busca alimento por ID específico |
| PUT | `/alimento/alterar/{id}` | Altera dados de um alimento por ID |
| DELETE | `/alimento/deletar/{id}` | Remove um alimento por ID |

### 2. Receitas (/receita)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/receita/adicionar` | Cadastra uma nova receita |
| GET | `/receita/listar` | Lista todas as receitas cadastradas |
| GET | `/receita/listar/{id}` | Busca receita por ID específico |
| PUT | `/receita/alterar/{id}` | Altera dados de uma receita por ID |
| DELETE | `/receita/deletar/{id}` | Remove uma receita por ID |
| GET | `/receita/sugerir` | Sugere receitas baseadas nos ingredientes disponíveis |

### 3. Sugestões IA (/ia)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/ia/sugerir-receitas` | Gera sugestões de receitas com base nos alimentos |
| POST | `/ia/analizar-ingredientes` | Analisa os ingredientes disponíveis |
| GET | `/ia/recomendacoes` | Retorna recomendações personalizadas |

## Configuração

O projeto utiliza um arquivo `.env` para configurações de ambiente. Certifique-se de configurar as variáveis de ambiente necessárias antes de executar a aplicação.
- <b>Variáveis de Ambiente</b>: `DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD, AI_API_KEY, AI_MODEL`

## Estrutura do Projeto

- `src/main/java/dev/nicolas/MagicFridgeAI/` - Código fonte principal
- `src/main/resources/templates/` - Templates HTML
- `src/main/resources/static/` - Arquivos estáticos (CSS, JS)
- `src/main/resources/data/` - Dados iniciais do banco
- `src/main/resources/db/migration/` - Migrações Flyway


