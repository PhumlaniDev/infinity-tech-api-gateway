# API Gateway

[![CI Workflow](https://github.com/PhumlaniDev/infinity-tech-api-gateway/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/PhumlaniDev/infinity-tech-api-gateway/actions/workflows/ci-cd.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=bugs)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=coverage)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=PhumlaniDev_infinity-tech-api-gateway&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=PhumlaniDev_infinity-tech-api-gateway)

## Overview

The `api-gateway` is a Spring Boot-based microservice that acts as a gateway for routing and managing requests to various backend services. It is built using Java and Maven, and it integrates with tools like SonarCloud for code quality and Docker for containerization.

## Features

- Centralized API routing and request management.
- Integration with SonarCloud for code quality analysis.
- Dockerized for easy deployment.
- CI/CD pipeline with GitHub Actions.
- Static and dynamic security testing workflows.

## Prerequisites

- Java 21
- Maven
- Docker
- GitHub account with necessary secrets configured for CI/CD.

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/PhumlaniDev/infinity-tech-api-gateway.git
cd infinity-tech-api-gateway
```

### Build the Project

```bash
mvn clean install -DskipTests
```

### Run the Application
```bash
java -jar target/api-gateway-<version>.jar
```

### Run with Docker
```bash
docker build -t api-gateway .
docker run -p 8080:8080 api-gateway
```

## CI/CD Pipeline
The CI/CD pipeline is configured using GitHub Actions. It includes the following workflows:
- **Checkstyle**: Ensures code adheres to style guidelines.
- **Build**: Compiles and packages the application.
- **SAST**: Static Application Security Testing.
- **SCA**: Software Composition Analysis.
- **Notification**: Sends status updates to Discord.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
