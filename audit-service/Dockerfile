FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build

COPY pom.xml ./
COPY audit-service/pom.xml ./audit-service/
COPY audit-service/.mvn ./audit-service/.mvn
COPY audit-service/mvnw ./audit-service/

RUN cd audit-service \
    && chmod +x mvnw \
    && ./mvnw -q -DskipTests dependency:go-offline

COPY audit-service/src ./audit-service/src

RUN cd audit-service && ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd -r -u 10001 appuser
USER 10001:10001

COPY --from=build /build/audit-service/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8085

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]