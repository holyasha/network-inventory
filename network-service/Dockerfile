FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build

COPY pom.xml ./
COPY network-service/pom.xml ./network-service/
COPY network-service/.mvn ./network-service/.mvn
COPY network-service/mvnw ./network-service/

RUN cd network-service \
    && chmod +x mvnw \
    && ./mvnw -q -DskipTests dependency:go-offline

COPY network-service/src ./network-service/src

RUN cd network-service && ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd -r -u 10001 appuser
USER 10001:10001

COPY --from=build /build/network-service/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8083

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]