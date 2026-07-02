# Docker Learning Notes for cloud-devops-portfolio-api

This document is a beginner-friendly but architecture-level guide for understanding Docker in the context of the project `cloud-devops-portfolio-api`.

Project details:
- Project name: `cloud-devops-portfolio-api`
- Docker image name: `portfolio-api`
- Container name: `portfolio-container`
- Application port: `8080`
- APIs:
  - `/health`
  - `/profile`
  - `/projects`

---

## 1. What problem Docker solves

Docker solves the biggest problem in software delivery: "It works on my machine, but not on yours."

Before Docker, applications often depended on:
- a specific operating system
- specific Java/JDK versions
- specific library versions
- external system setup

That made deployments inconsistent across developer laptops, testing environments, and production servers.

Docker solves this by packaging the application and its dependencies into a portable unit called an image.

### Simple idea

Instead of saying:
- "Install Java 17"
- "Install Maven"
- "Install libraries"
- "Run the app"

You say:
- "Run this Docker image"

That image already contains everything the app needs.

### Why this matters

Docker gives us:
- portability
- consistency
- faster setup
- easier scaling
- better CI/CD automation

---

## 2. Difference between normal Java deployment and Docker deployment

### Normal Java deployment

A normal Java deployment usually looks like this:

1. Install Java on the server
2. Copy the jar file
3. Run the jar with `java -jar app.jar`
4. Hope the environment matches the developer machine

Example:

```powershell
java -jar target/cloud-devops-portfolio-api-0.0.1-SNAPSHOT.jar
```

### Problems with normal deployment

- environment mismatch
- missing dependencies
- different Java versions
- hard to reproduce
- difficult to move between machines

### Docker deployment

With Docker:

1. Build an image from the application code
2. Run the image as a container
3. The container already contains the runtime and dependencies

Example:

```powershell
docker build -t portfolio-api .
docker run -d --name portfolio-container -p 8080:8080 portfolio-api
```

### Key difference

- Normal deployment depends on the host machine environment
- Docker deployment packages the environment with the application

---

## 3. Docker architecture

Docker architecture is built around a few simple components.

```text
Developer / CI Pipeline
        |
        v
Docker Client (docker build, docker run, docker push)
        |
        v
Docker Daemon (background engine)
        |
        +--> Images
        +--> Containers
        +--> Networks
        +--> Volumes
```

### Main components

- Docker Client: the command-line tool you use
- Docker Daemon: the service that manages containers and images
- Images: read-only templates used to create containers
- Containers: running instances of images
- Registry: storage for images, such as Docker Hub or Amazon ECR

### Simple real-world analogy

- Image = a recipe or blueprint
- Container = the actual dish prepared from that recipe

---

## 4. Dockerfile explanation line by line

Your Dockerfile is the instruction file that tells Docker how to build the image.

```dockerfile
# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/cloud-devops-portfolio-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Line-by-line explanation

1. `# Build stage`
   - Comment for readability.

2. `FROM maven:3.9.9-eclipse-temurin-17 AS build`
   - Starts from a base image that already has Maven and Java 17.
   - `AS build` gives this stage a name so we can reuse it later.

3. `WORKDIR /app`
   - Sets the working directory inside the container to `/app`.

4. `COPY pom.xml .`
   - Copies the Maven build file into the container.

5. `RUN mvn -B dependency:go-offline`
   - Downloads dependencies before copying the full source code.
   - This improves build caching and speed.

6. `COPY src ./src`
   - Copies the application source code into the container.

7. `RUN mvn -B clean package`
   - Builds the Spring Boot jar file.

8. `# Runtime stage`
   - Starts a second stage for the runtime environment.

9. `FROM eclipse-temurin:17-jre-alpine`
   - Uses a smaller Java runtime image for the final app.
   - This keeps the final image lightweight.

10. `WORKDIR /app`
   - Creates the runtime working directory.

11. `RUN addgroup -S spring && adduser -S spring -G spring`
   - Creates a non-root user for better security.

12. `USER spring:spring`
   - Switches the container process to the created user.

13. `COPY --from=build /app/target/cloud-devops-portfolio-api-0.0.1-SNAPSHOT.jar app.jar`
   - Copies only the built jar from the build stage into the runtime stage.
   - This is the core of the multi-stage build.

14. `EXPOSE 8080`
   - Declares that the application listens on port `8080`.

15. `ENTRYPOINT ["java", "-jar", "app.jar"]`
   - Tells Docker what command to run when the container starts.

### Why this Dockerfile is good

- It uses a multi-stage build
- It keeps the final image smaller
- It avoids running the app as root
- It is easy to understand and maintain

---

## 5. Docker image explanation

A Docker image is a packaged, read-only blueprint of your application.

It contains:
- the application code
- runtime environment
- libraries
- configuration
- startup command

### Think of it like this

- Image = a snapshot/template
- Container = a running instance of that snapshot

### Example

```powershell
docker build -t portfolio-api .
```

This command creates an image named `portfolio-api` based on your Dockerfile.

---

## 6. Docker container explanation

A container is a running process created from a Docker image.

It is isolated and lightweight, and it runs your application in a controlled environment.

### Example

```powershell
docker run -d --name portfolio-container -p 8080:8080 portfolio-api
```

This creates and starts a container named `portfolio-container` from the image `portfolio-api`.

---

## 7. Difference between Docker image and container

```text
Docker Image                     Docker Container
------------                     ---------------
Blueprint / template            Running instance
Read-only                       Read-write layer on top
Used to create containers      Created from an image
Does not run by itself          Runs the application
```

### In simple words

- Image = the recipe
- Container = the cooked meal

---

## 8. Explanation of the docker build command

The `docker build` command creates a Docker image from a Dockerfile.

### Basic syntax

```powershell
docker build -t portfolio-api .
```

### Explanation

- `build` = create an image
- `-t portfolio-api` = tag the image with the name `portfolio-api`
- `.` = use the current folder as the build context

### Important note

The build context includes the files Docker can access while building the image, such as the Dockerfile and application source.

---

## 9. Explanation of the docker images command

The `docker images` command lists all local Docker images.

```powershell
docker images
```

### Example output

```text
REPOSITORY      TAG       IMAGE ID       SIZE
portfolio-api   latest    abc123...      250MB
```

This helps you see which images exist locally.

---

## 10. Explanation of the docker run command

The `docker run` command creates and starts a container from an image.

```powershell
docker run -d --name portfolio-container -p 8080:8080 portfolio-api
```

### Explanation

- `-d` = run in detached mode (in the background)
- `--name portfolio-container` = give the container a custom name
- `-p 8080:8080` = publish the port
- `portfolio-api` = the image to run

### What happens here

Your Spring Boot app inside the container starts on port `8080`, and Docker maps that port to your host machine.

---

## 11. Explanation of `-p 8080:8080` port mapping

The syntax `-p 8080:8080` means:

```text
host_port:container_port
```

So:
- `8080` on your machine
- maps to `8080` inside the container

### Why it is needed

Your app inside Docker listens on `8080`, but your browser or curl command runs from outside the container. Docker needs to expose that port to the host.

### Example

```powershell
curl http://localhost:8080/health
```

This works because Docker forwarded traffic from your machine's `8080` to the container's `8080`.

---

## 12. Explanation of `docker ps`, `docker logs`, `docker stop`

### `docker ps`
Shows currently running containers.

```powershell
docker ps
```

### `docker logs`
Shows the logs of a container.

```powershell
docker logs portfolio-container
```

### `docker stop`
Stops a running container.

```powershell
docker stop portfolio-container
```

### Useful flow

```powershell
docker ps
docker logs portfolio-container
docker stop portfolio-container
```

---

## 13. Where Docker images are stored on Windows with Docker Desktop and WSL2

On Windows, Docker Desktop usually uses WSL2 as the backend.

### In simple terms

Docker images are not stored in the normal Windows file system the way a regular app might be. They live inside the WSL2 environment that Docker Desktop manages.

### Common storage location concept

You may see paths similar to:

```text
\\wsl$\docker-desktop-data\version-pack-data\community\docker
```

Or you can inspect the Docker root directory using:

```powershell
docker info --format '{{.DockerRootDir}}'
```

### Important understanding

- Docker Desktop manages storage for images and containers
- WSL2 is the underlying Linux environment used by Docker
- You usually do not need to access these files manually

---

## 14. How to export image using `docker save`

You can save a Docker image to a file so it can be moved to another machine.

```powershell
docker save -o portfolio-api.tar portfolio-api
```

### Explanation

- `-o` = output file
- `portfolio-api.tar` = the file that will be created
- `portfolio-api` = the image name

### Why this is useful

You can transfer the image offline or through shared storage.

---

## 15. How to import image using `docker load`

On another system, you can load the image from the tar file.

```powershell
docker load -i portfolio-api.tar
```

### Explanation

- `-i` = input file
- This restores the image into the local Docker environment

---

## 16. How to share an image with another system

You can share a Docker image in one of these ways:

1. Save as a tar file using `docker save`
2. Transfer the tar file using USB, email, cloud storage, or network sharing
3. Load it on the other system using `docker load`

### Example flow

```powershell
# On source machine
docker save -o portfolio-api.tar portfolio-api

# Transfer the file to another system

# On target machine
docker load -i portfolio-api.tar
```

---

## 17. Prerequisites needed on another system

Before running the image on another machine, the target system should have:

- Docker Desktop or Docker Engine installed
- enough disk space
- the correct CPU and memory resources
- access to the image file or registry

### Minimal requirement for a simple run

```powershell
docker run -d --name portfolio-container -p 8080:8080 portfolio-api
```

---

## 18. What Docker Hub is

Docker Hub is the default public registry for Docker images.

It is like an app store for container images.

You can:
- pull existing public images
- push your own images
- share images with teams or the world

### Example public image

```powershell
docker pull nginx
```

---

## 19. Difference between Docker Hub, Kubernetes, and AWS ECS

```text
Docker Hub        Registry for storing and sharing container images
Kubernetes       Container orchestration platform for running containers at scale
AWS ECS          Managed container runtime/orchestration service on AWS
```

### Simple difference

- Docker Hub = image storage and sharing
- Kubernetes = deployment and orchestration
- AWS ECS = managed cloud container service

### Relationship

A typical flow is:

```text
Build image -> Push to Docker Hub/ECR -> Deploy with Kubernetes or ECS
```

---

## 20. Why Kubernetes needs Docker Hub/ECR/registry

Kubernetes does not magically know where your image is located.

It needs a registry to pull the image from.

### Why

- Kubernetes schedules containers on nodes
- Each node must be able to pull the image
- The image must be available in a shared registry

### Example

Kubernetes cannot directly use a local image from your laptop unless:
- it is pushed to a registry
- or you run a local registry

---

## 21. How to push image to Docker Hub

First, log in:

```powershell
docker login
```

Then tag the image:

```powershell
docker tag portfolio-api your-dockerhub-username/portfolio-api:latest
```

Then push it:

```powershell
docker push your-dockerhub-username/portfolio-api:latest
```

### Example using a sample username

```powershell
docker tag portfolio-api muniprasad123/portfolio-api:latest
docker push muniprasad123/portfolio-api:latest
```

---

## 22. How another system can pull and run the image

On another machine:

```powershell
docker pull your-dockerhub-username/portfolio-api:latest
docker run -d --name portfolio-container -p 8080:8080 your-dockerhub-username/portfolio-api:latest
```

This is how a team or a server can run the same image.

---

## 23. What image immutability means

Image immutability means:

Once an image is built, it should not be changed in place.

If you need a change:
- update the Dockerfile
- rebuild the image
- create a new version/tag

### Example

```powershell
docker build -t portfolio-api:v2 .
```

This creates a new immutable image version rather than changing the old one.

---

## 24. How to add new dependencies or tools into the image

You add them in the Dockerfile.

### Example

If you want to install `curl` in the runtime image:

```dockerfile
RUN apk add --no-cache curl
```

Or if you need a new Java library, you update `pom.xml` and rebuild the image.

### Important principle

You do not modify a container and expect the image to magically update. You change the Dockerfile and rebuild.

---

## 25. Why we rebuild image instead of modifying old image

Because the image should remain a consistent artifact.

If you change a running container manually:
- the change is temporary
- it is not reproducible
- it is not versioned
- it breaks the purpose of image-based deployment

### Better approach

```powershell
# change Dockerfile
# rebuild image
# deploy new image
```

This makes your deployment repeatable and reliable.

---

## 26. Real company CI/CD flow

A modern real-world flow looks like this:

```text
Developer pushes code to GitHub
   |
   v
Jenkins / GitHub Actions / Azure DevOps
   |
   v
Build and run tests
   |
   v
Build Docker image
   |
   v
Push image to registry (Docker Hub / ECR / Artifact Registry)
   |
   v
Deploy to Kubernetes / ECS / VM
   |
   v
Health checks and monitoring
```

### In this project

The same pattern would be:

1. code change in `cloud-devops-portfolio-api`
2. Maven tests run
3. Docker image built
4. image pushed to registry
5. deployment to Kubernetes/ECS

---

## 27. Interview questions and answers

### Q1. What is Docker?
A. Docker is a platform that packages applications and their dependencies into containers so they can run consistently across environments.

### Q2. What is the difference between image and container?
A. An image is a blueprint; a container is a running instance of that image.

### Q3. Why is Docker used in DevOps?
A. It improves portability, consistency, repeatability, and deployment speed.

### Q4. What is a Dockerfile?
A. A Dockerfile is a text file with instructions to build a Docker image.

### Q5. Why use multi-stage Docker builds?
A. They keep the final image smaller and improve build efficiency by separating build tools from runtime tools.

### Q6. What is the purpose of `-p 8080:8080`?
A. It maps the host machine port to the container port so the application can be accessed from outside the container.

### Q7. Why do we rebuild the image instead of editing the container directly?
A. Because containers are temporary runtime instances, while images are versioned and reproducible artifacts.

### Q8. What is Docker Hub?
A. Docker Hub is a public/private registry for sharing and storing Docker images.

### Q9. Why does Kubernetes need a registry?
A. Kubernetes needs a registry to pull the container image onto worker nodes.

### Q10. What is image immutability?
A. It means an image should not be changed after it is built; changes require a new image version.

---

## 28. Hands-on practice checklist

Use this checklist to build confidence:

- [ ] Build the image locally
  ```powershell
  docker build -t portfolio-api .
  ```
- [ ] List the local images
  ```powershell
  docker images
  ```
- [ ] Run the container
  ```powershell
  docker run -d --name portfolio-container -p 8080:8080 portfolio-api
  ```
- [ ] Check the running container
  ```powershell
  docker ps
  ```
- [ ] Test the app endpoints
  ```powershell
  curl http://localhost:8080/health
  curl http://localhost:8080/profile
  curl http://localhost:8080/projects
  ```
- [ ] View container logs
  ```powershell
  docker logs portfolio-container
  ```
- [ ] Stop the container
  ```powershell
  docker stop portfolio-container
  ```
- [ ] Save the image to a tar file
  ```powershell
  docker save -o portfolio-api.tar portfolio-api
  ```
- [ ] Load the image on another machine
  ```powershell
  docker load -i portfolio-api.tar
  ```
- [ ] Push the image to Docker Hub
  ```powershell
  docker login
  docker tag portfolio-api your-dockerhub-username/portfolio-api:latest
  docker push your-dockerhub-username/portfolio-api:latest
  ```
- [ ] Pull and run the image from Docker Hub
  ```powershell
  docker pull your-dockerhub-username/portfolio-api:latest
  docker run -d --name portfolio-container -p 8080:8080 your-dockerhub-username/portfolio-api:latest
  ```

---

## Questions I Asked My Mentor

### 1. Where is Docker image stored?
Answer:
Docker images are stored in the Docker runtime storage area managed by Docker Desktop and WSL2. On Windows, this is usually inside the WSL2-backed Docker environment rather than in a normal Windows folder.

### 2. How to share Docker image to another system?
Answer:
You can save the image as a tar file using `docker save`, transfer it to another system, and load it with `docker load`.

### 3. What prerequisites are needed on another system?
Answer:
The target system needs Docker installed, enough resources, and access to the image file or registry.

### 4. If image is immutable, how to add more dependencies?
Answer:
You update the Dockerfile, add the dependency or tool, rebuild the image, and create a new version/tag.

### 5. Difference between Docker Hub and Kubernetes/ECS?
Answer:
Docker Hub is an image registry. Kubernetes and ECS are platforms used to run containers.

### 6. Why Kubernetes cannot directly use my laptop image?
Answer:
Because the image is local to your machine. Kubernetes needs the image available in a shared registry such as Docker Hub, ECR, or a private registry.

---

## Final mentor advice

Docker is not just a tool for packaging. It is a mindset for building portable, scalable, and repeatable software.

For this project:
- your image is `portfolio-api`
- your container is `portfolio-container`
- your app runs on `8080`
- your APIs are `/health`, `/profile`, and `/projects`

As you grow in DevOps, remember this:

```text
Code -> Build -> Package -> Run -> Share -> Deploy -> Scale
```

That is the real journey from developer to solution architect.
