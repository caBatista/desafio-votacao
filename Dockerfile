FROM openjdk:17
WORKDIR /app
COPY build/libs/votacao.jar /app
EXPOSE 8080
CMD ["java", "-jar", "financial-dashboard.jar"]