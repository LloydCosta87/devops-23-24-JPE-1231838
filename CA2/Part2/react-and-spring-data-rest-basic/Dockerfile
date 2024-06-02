# Use a imagem oficial do Tomcat como base
FROM tomcat:9.0

# Copie o arquivo .war para o diretório webapps do Tomcat
COPY build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta 8080
EXPOSE 8080

# Comando para iniciar o Tomcat
CMD ["catalina.sh", "run"]
