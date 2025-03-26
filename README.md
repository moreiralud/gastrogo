# GastroGo

GastroGo é uma API REST para gerenciamento de restaurantes, reservas e avaliações, desenvolvida com Spring Boot e MongoDB. A aplicação pode ser executada via Docker, permitindo deploy em ambientes diversos.

---

## Pré-requisitos

- [Docker e Docker Compose](https://docs.docker.com/get-docker/)
- (Opcional) Java 17 e Maven para execução local

---

## Configuração de Ambiente

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto (adicione-o ao `.gitignore` para não expor dados sensíveis):

```properties
# Exemplo para MongoDB Atlas
SPRING_DATA_MONGODB_URI=mongodb+srv://gastrogo:senha@cluster0.afewq.mongodb.net/SeuDatabase?retryWrites=true&w=majority&appName=Cluster0
SPRING_PROFILES_ACTIVE=prod

# Ou, para MongoDB local via Docker:
# SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/gastrogo

Importante: Remova os < e > e substitua pelos valores reais.
```
## Docker Compose

O arquivo docker-compose.yml está configurado para levantar dois containers (aplicação e MongoDB):

```
version: '3.8'
services:
mongo:
image: mongo:6.0
ports:
- "27017:27017"
volumes:
- mongo-data:/data/db

gastrogo-app:
build: .
ports:
- "8080:8080"
environment:
SPRING_DATA_MONGODB_URI: ${SPRING_DATA_MONGODB_URI}
SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE}
depends_on:
- mongo

volumes:
mongo-data: {}

```

## Execução via Docker Compose
Na raiz do projeto, execute:

```
docker compose up --build
```

A aplicação estará disponível em http://localhost:8080 e a documentação Swagger em http://localhost:8080/swagger-ui.html.

## Testes com Postman

## Exportar a Coleção:

No Postman, exporte a coleção de exemplos para um arquivo JSON (ex.: GastroGo.postman_collection.json)

## Importar no Postman:

Clique em "Import" no Postman e selecione o arquivo JSON: GastroGo.postman_collection.json disponível na raiz do projeto

