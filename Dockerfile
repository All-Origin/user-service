# Doekerfile Flow and Notes

#---------------------------
# Stage 1: Build the Spring Boot Application
#---------------------------
# 1. Use Eclipse Temurin JDK 17 as build image (provides Java compiler and tools)
FROM eclipse-temurin:17-jdk AS build
LABEL authors="Jeet solanki"

# 2. Set the working directory inside the container to /app
WORKDIR /app

# 3. Copy all project files from the host into the container's /app directory
COPY . .

# 4. Ensure the Maven wraper sricpt (mvnw) executable
RUN chmod +x ./mvnw

# 5. Build the application using Maven, Skipping tests for faster build
RUN ./mvnw clean package -DskipTests

#----------------------------
# Stage 2: Create a LightWeight Runtime Image
#----------------------------
# 6. Use Eclipse Temurin JRE 17 as the runtime image (smaller, no compiler jsut runtime support)
FROM eclipse-temurin:17-jre

# 7. Set the working directory insde the runtime container to /app
WORKDIR /app

# 8. Copy the built JAR file from the build stage into the runtime image
COPY --from=build /app/target/*.jar app.jar

# 9 Expose port 8081 (defout is in the use in auth service)
EXPOSE 8081

# 10. Set the default command to run the spring boot application
CMD ["java","-jar", "app.jar"]


#------------------------------
#Notes:
# - This Dockerfile uses multi-stage to keep the final image small and secure
# - The build stage compilse the code and packages it as a JAR.
# - The runtime stage only contains the JRE and paclaged JAR, redusing attack surface and image size.
# - The application will be accessible on port 8081.
# - The Maven wrapper (mvnw) ensures consistent Maven version usage across enviroments.
