FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build

COPY pom.xml ./
COPY api-gateway/pom.xml ./api-gateway/
COPY api-gateway/.mvn ./api-gateway/.mvn
COPY api-gateway/mvnw ./api-gateway/

RUN cd api-gateway \
    && chmod +x mvnw \
    && ./mvnw -q -DskipTests dependency:go-offline

COPY api-gateway/src ./api-gateway/src

RUN cd api-gateway && ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd -r -u 10001 appuser
USER 10001:10001

COPY --from=build /build/api-gateway/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]