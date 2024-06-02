# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Adicione o arquivo WAR da sua aplicação
ADD build/libs/react-and-spring-data-rest-basic.war /app.war

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app.war"]
