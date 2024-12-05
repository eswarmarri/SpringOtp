# Spring Boot Otp Project

This is a simple Spring Boot application built using Gradle. The project demonstrates generating and verifying otp using in memory cache.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Author](#author)

## Prerequisites

Before you begin, ensure you have met the following requirements:
- You have installed [JDK 11](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or higher.
- You have installed [Gradle](https://gradle.org/install/).
- You have aws SES account.

## Installation

1. Clone this repository:

    ```sh
    git clone https://github.com/yourusername/spring-boot-gradle.git
    cd spring-boot-gradle
    ```

2. Resolve dependencies and build the project:

    ```sh
    ./gradlew build
    ```

## Running the Application
Before running the application, make sure to replace application.yml properties with your aws credentials.

To run the application, use the following command:

```sh
./gradlew bootRun
```
You can access the application at `http://localhost:8080/`

Access Swagger for this application at `http://localhost:8080/swagger-ui/index.html#/` to test out the api's.
## Author
Eswar Reddy Marri
`eswarmarri99@gmail.com`
