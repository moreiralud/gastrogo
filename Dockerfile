# 1ª Etapa: build (compilar o projeto)
FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /app

# Copia os arquivos de configuração do Maven (pom.xml, etc.)
COPY pom.xml ./

# Faz download das dependências para cachear no Docker (acelera rebuilds)
RUN mvn dependency:go-offline

# Copia o restante do código-fonte do projeto
COPY src ./src

# Executa o Maven para gerar o .jar (skipTests para agilizar)
RUN mvn clean package -DskipTests

# 2ª Etapa: imagem final, apenas com o JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o .jar gerado da etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# (Opcional) Define o profile do Spring a ser usado
ENV SPRING_PROFILES_ACTIVE=prod

# Expõe a porta 8080 para o contêiner
EXPOSE 8080

# Define o comando de inicialização
ENTRYPOINT ["java","-jar","app.jar"]
