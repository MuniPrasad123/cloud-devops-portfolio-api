# Docker Day 1 Notes

## Why Docker Exists

Docker exists to solve a common software problem: applications behave differently across machines.

Before Docker, teams often faced:
- Environment mismatch between development, testing, and production
- Manual server setup and dependency installation
- The phrase "works on my machine"

Docker packages an application and its dependencies into a portable unit called an image. That image can run the same way on any machine with Docker installed.

## The Problem Before Docker

Imagine a Java Spring Boot app that runs on one developer laptop but fails on another machine because:
- Java version is different
- Maven or dependencies are missing
- OS-level libraries are different
- Port configuration is inconsistent

Docker removes this friction by standardizing the runtime environment.

## Docker Architecture Overview

A simple way to understand Docker is:

```text
Developer Machine
    |
    | docker build / docker run
    v
Docker CLI
    |
    v
Docker Engine / Docker Daemon
    |
    v
Containers and Images
```

### Main components
- Docker CLI: the command-line tool used by developers to interact with Docker
- Docker Engine: the core runtime that manages containers
- Docker Daemon: the background service that builds, runs, and manages containers

## Dockerfile Explained

A Dockerfile is a plain-text instruction file that defines how to build a Docker image.

It tells Docker:
- which base image to start from
- what files to copy
- which commands to run
- how the application should start

### Common Dockerfile instructions
- FROM: choose the base image
- WORKDIR: set the working directory inside the container
- COPY: copy files from your machine into the image
- ENTRYPOINT: define the command that starts the app

## Image vs Container

This is one of the most important Docker concepts.

### Docker Image
- A read-only blueprint
- Contains application code, dependencies, and runtime settings
- Immutable by design

### Docker Container
- A running instance of an image
- Has a writable layer on top
- Can be started, stopped, removed, and recreated

```text
Docker Image (blueprint) --> Docker Container (running instance)
```

## Image Immutability

Docker images are immutable. Once an image is built, its content is preserved.

If you change the application, you create a new image version rather than modifying the old one in place.

This makes deployments predictable and easier to rollback.

## Docker Layer Caching

Docker builds images in layers.

Each instruction in a Dockerfile creates a layer, such as:
- base image layer
- copied files layer
- installed dependencies layer

If nothing changes in a layer, Docker can reuse it from cache. This makes rebuilds much faster.

## Why Dockerfile Order Matters

The order of instructions in a Dockerfile matters because Docker caches layers based on the instruction and its content.

If you place frequently changing steps too early, Docker will rebuild later steps more often.

### Good practice
Put stable instructions first and changing instructions later.

Example:
```dockerfile
FROM maven:3.9.9-eclipse-temurin-17
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
ENTRYPOINT ["java", "-jar", "target/app.jar"]
```

This is better than copying the entire source code first, because Maven dependency download layers can be cached.

## Optimized Dockerfile Example for This Project

For the Spring Boot project, an optimized Dockerfile should copy the Maven files first and then the source code.

```dockerfile
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace/app
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Why this is better
- It separates build and runtime stages
- It uses dependency caching effectively
- It keeps the final image smaller and cleaner

## What docker build Does

The command below builds an image from a Dockerfile:

```bash
docker build -t portfolio-api .
```

This means:
- build an image named `portfolio-api`
- use the current folder as the build context
- read instructions from the Dockerfile in that folder

## What docker run Does

The command below starts a container from the image:

```bash
docker run -d --name portfolio-container -p 8080:8080 portfolio-api
```

### Meaning of the flags
- `-d`: run in detached mode
- `--name`: give the container a name
- `-p 8080:8080`: map host port to container port

## Port Mapping

Port mapping connects a port on your machine to a port inside the container.

```bash
-p 8080:8080
```

This means:
- your host machine can access the app on port `8080`
- inside the container, the app is listening on port `8080`

For this project, the app endpoints are:
- `/health`
- `/profile`
- `/projects`

## Useful Day 1 Commands

### List running containers
```bash
docker ps
```

### List all containers including stopped ones
```bash
docker ps -a
```

### Stop a container
```bash
docker stop portfolio-container
```

### Start a stopped container
```bash
docker start portfolio-container
```

## docker stop vs docker start

- `docker stop`: gracefully stops a running container
- `docker start`: starts an already existing but stopped container

## Container Name Conflict

If you try to run a container with a name that already exists, Docker will show an error.

Example:
```bash
docker run --name portfolio-container portfolio-api
```

If the name is already in use, you can:
- stop and remove the old container
- or use a new name such as `modest_lamport`

Example:
```bash
docker rm -f portfolio-container
docker run -d --name modest_lamport -p 8080:8080 portfolio-api
```

## Day 1 Reflection

Day 1 focused on understanding the foundation of Docker:
- why it exists
- how it works
- how images and containers differ
- how a Dockerfile creates repeatable environments

This is the base knowledge needed for real DevOps workflows.
