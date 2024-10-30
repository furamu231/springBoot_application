FROM eclipse-temurin:17   
WORKDIR /app
COPY . /app
RUN ./gradlew build -x test
CMD ["java", "-jar", "build/libs/spring-0.0.1-SNAPSHOT.jar"]