# Stage 1: Build the WAR
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -B -DskipTests clean package

# Stage 2: Run WAR on Tomcat 10 (supports jakarta.*)
FROM tomcat:10-jdk17

# Remove default apps and copy ours
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Disable Tomcat shutdown port (prevent "Invalid shutdown command" logs)
# This replaces port="8005" with port="0" in server.xml
RUN sed -i 's/port="8005"/port="0"/' /usr/local/tomcat/conf/server.xml

EXPOSE 8080
