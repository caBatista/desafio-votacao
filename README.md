# DBServer - Desafio Votação

Este projeto é uma aplicação Spring Boot que fornece uma API REST para gerenciar sessões de votação em assembleias cooperativas.

## Tecnologias utilizadas

- Java 17
- Spring Boot 3.3.0
- Gradle
- Jib 3.1.4
- PostgreSQL
- Docker

## Preparando o ambiente
Como pré-requisito, é necessário ter o Docker e o Java JDK 17+ instalados na máquina.

## Como executar o projeto

1. Clone o repositório para a sua máquina local usando `git clone`.

```bash
git clone https://github.com/caBatista/desafio-votacao.git
```

2. Navegue até o diretório do projeto.
```bash
cd desafio-votacao
```

3. Execute o projeto com o Gradle Wrapper.

```bash
./gradlew build
```

O comando build irá fazer o download das dependências, compilar o código e executar os testes unitários, além de rodar automaticamente o comando `docker-compose up --build`, que vai construir os serviços de Backend e Banco de Dados.

## Endpoints da API

Como principais endpoints criados, estão disponíveis:

- `POST /api/v1/pautas`: Cria uma nova pauta.
- `POST /api/v1/pautas/{id}/sessoes`: Cria uma nova sessão de votação para uma pauta.
- `POST /api/v1/pautas/{id}/votos`: Registra um voto para uma pauta.
- `POST /api/v1/pautas/{id}/encerra`: Encerra uma pauta e apresenta seu resultado.

Além destes, outros endpoints estão disponíveis para consulta no Swagger, que pode ser acessado em `http://localhost:8080/swagger-ui.html` quando a aplica
cão estiver rodando.
