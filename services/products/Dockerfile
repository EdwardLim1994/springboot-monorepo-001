FROM gradle:9.8-jdk25 AS build
WORKDIR /workspace
COPY . .
RUN gradle :services:products:bootJar --no-daemon

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/services/products/build/libs/products-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
