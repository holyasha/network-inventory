FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build

COPY pom.xml ./
COPY location-service/pom.xml ./location-service/
COPY location-service/.mvn ./location-service/.mvn
COPY location-service/mvnw ./location-service/

RUN cd location-service \
    && chmod +x mvnw \
    && ./mvnw -q -DskipTests dependency:go-offline

COPY location-service/src ./location-service/src

RUN cd location-service && ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd -r -u 10001 appuser
USER 10001:10001

COPY --from=build /build/location-service/target/*.jar /app/app.jar

ENV JAVA_OPTS=""
EXPOSE 8082

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]