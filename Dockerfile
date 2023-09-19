# Use uma imagem oficial do OpenJDK como base
FROM openjdk:11-jre-slim

# Defina o diretório de trabalho dentro da imagem
WORKDIR /app

# Copie o arquivo JAR construído pelo Maven para o diretório de trabalho
COPY target/teste-deliverit.jar ./app.jar

# Exponha a porta que o aplicativo Java irá escutar (ajuste conforme necessário)
EXPOSE 8080

# Comando para executar o aplicativo Java
CMD ["java", "-jar", "app.jar"]
