# Use uma imagem base com OpenJDK
FROM openjdk:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo WAR gerado para o diretório de trabalho no container
COPY build/libs/*.war app.war

# Expõe a porta que a aplicação irá rodar
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.war"]
