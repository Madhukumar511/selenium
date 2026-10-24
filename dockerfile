FROM selenium/standalone-chrome:4.8.3-20230328 AS base

USER root

# Install Maven dependency components cleanly
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
COPY testng.xml .

COPY src/ ./src/
COPY webapp/ ./webapp/

CMD ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]
