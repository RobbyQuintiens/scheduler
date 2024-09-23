# Stage 1: Build the application
FROM eclipse-temurin:11-jdk AS build

# Install maven
RUN apt-get update && apt-get install -y wget \
    && wget https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz \
    && tar xzf apache-maven-3.6.3-bin.tar.gz -C /opt \
    && ln -s /opt/apache-maven-3.6.3/bin/mvn /usr/bin/mvn \
    && rm apache-maven-3.6.3-bin.tar.gz

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

# Stage 2: Run the application using the Java 11 JRE
FROM eclipse-temurin:11-jre

# Set the working directory for the runtime
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the necessary port
EXPOSE 8080

# Define the entrypoint command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
