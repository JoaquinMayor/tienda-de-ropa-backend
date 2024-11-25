# Use a base image with Maven installed
FROM maven:3.8.6-amazoncorretto-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn package

# Use a minimal base image for the final container
FROM amazoncorretto:17-alpine-jdk

# Copy the built JAR file from the build stage
COPY --from=build /app/target/tienda_ropa-0.0.1-SNAPSHOT.jar app.jar

# Set the entry point
ENTRYPOINT ["java", "-jar", "/app.jar"]