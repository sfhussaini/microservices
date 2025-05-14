# Use an OpenJDK base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container Image
COPY target/InventoryService-0.0.1-SNAPSHOT.jar InventoryService.jar

# Expose the application port
EXPOSE 7070

# Run the application
CMD ["java", "-jar", "InventoryService.jar"]