FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build

COPY pom.xml ./
COPY auth-service/pom.xml ./auth-service/
COPY auth-service/.mvn ./auth-service/.mvn
COPY auth-service/mvnw ./auth-service/

RUN cd auth-service \
    && chmod +x mvnw \
    && ./mvnw -q -DskipTests dependency:go-offline

COPY auth-service/src ./auth-service/src

RUN cd auth-service && ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd -r -u 10001 appuser
USER 10001:10001

COPY --from=build /build/auth-service/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8084

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]