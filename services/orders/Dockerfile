FROM gradle:9.8-jdk25 AS build
WORKDIR /workspace
COPY . .
RUN gradle :services:orders:bootJar --no-daemon

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/services/orders/build/libs/orders-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
