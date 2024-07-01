# Stage 1: Build
FROM openjdk:17-alpine as builder
WORKDIR /GestionSalonDeBelleza
COPY . /GestionSalonDeBelleza
#RUN ./gradlew build

# Stage 2: Production image
FROM openjdk:17-alpine
VOLUME /app/logs/GestionSalonDeBelleza
ARG PORT=8080
ENV SERVER_PORT=${PORT}
EXPOSE ${PORT}
WORKDIR /app
COPY --from=builder /GestionSalonDeBelleza/build/libs/GestionSalonDeBelleza-0.0.1-SNAPSHOT.jar /app/GestionSalonDeBelleza.jar
CMD ["sh", "-c", "java -jar -Dserver.port=${SERVER_PORT} /app/GestionSalonDeBelleza.jar"]
