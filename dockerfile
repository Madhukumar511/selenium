# Step 1: Use an official pre-configured Selenium image that has Java 11 and Chrome built-in
FROM selenium/standalone-chrome:4.8.3-20230328 AS base

# Switch to root user to install project build tools
USER root

# Install Maven so we can compile and execute our TestNG tests
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy your project configuration and manifest files
COPY pom.xml .
COPY testng.xml .

# Copy your local case-sensitive source code directories
COPY src/ ./src/
COPY webapp/ ./webapp/

# Default command to execute the TestNG automation test suite using Maven
CMD ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]
