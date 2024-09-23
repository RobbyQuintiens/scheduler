# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file and download dependencies (caches dependencies)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:resolve

# Copy the rest of the application source code
COPY src ./src

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Run the application using the Java 21 JRE
FROM eclipse-temurin:21-jre

# Set the working directory for the runtime
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the necessary port
EXPOSE 8080

# Define the entrypoint command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
