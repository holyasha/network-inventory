FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build

COPY pom.xml ./
COPY device-service/pom.xml ./device-service/
COPY device-service/.mvn ./device-service/.mvn
COPY device-service/mvnw ./device-service/

RUN cd device-service \
    && chmod +x mvnw \
    && ./mvnw -q -DskipTests dependency:go-offline

COPY device-service/src ./device-service/src

RUN cd device-service && ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd -r -u 10001 appuser
USER 10001:10001

COPY --from=build /build/device-service/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]