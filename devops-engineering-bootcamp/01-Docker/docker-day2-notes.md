# Docker Day 2 Notes

## Day 2 Focus

Day 2 moved from basic container creation to debugging, inspection, sharing images, and understanding the real-world DevOps workflow.

## Viewing Container Logs

Docker logs help you inspect what a container is doing.

```bash
docker logs portfolio-container
```

### Follow logs in real time
```bash
docker logs -f portfolio-container
```

This is very useful when the app fails to start or when you want to watch runtime behavior live.

## Executing Commands Inside a Container

Sometimes you need to enter a running container and inspect its filesystem.

```bash
docker exec -it portfolio-container sh
```

This opens a shell inside the container.

### What you can check inside
- whether the app jar exists
- environment variables
- file structure
- logs and runtime config

Example:
```bash
ls
find . -name "*.jar"
```

## Entering a Container and Checking app.jar

For a Spring Boot application, checking the packaged jar inside the container is a good debugging step.

```bash
docker exec -it portfolio-container sh
ls /app
```

If the jar is present, it usually means the container image was built correctly.

## Removing Containers

When you no longer need a container, remove it.

```bash
docker rm portfolio-container
```

To remove a running container forcefully:
```bash
docker rm -f portfolio-container
```

## Saving an Image to a File

You can save a Docker image as a tar file for transfer to another machine.

```bash
docker save -o portfolio-api.tar portfolio-api
```

This is useful when network access is restricted or you want to transfer images offline.

## Loading an Image from a File

On another system, load the saved image:

```bash
docker load -i portfolio-api.tar
```

## Sharing Images to Another System

There are two common ways to share Docker images:
1. Save/load as a tar file
2. Push to a registry such as Docker Hub

For team collaboration and cloud deployment, pushing to a registry is the preferred approach.

## Docker Hub Concept

Docker Hub is a public or private registry where Docker images are stored and shared.

It is similar to GitHub but for container images instead of source code.

### Example image reference
```bash
muniprasad07/portfolio-api:v1
```

## GitHub vs Docker Hub

| Topic | GitHub | Docker Hub |
|---|---|---|
| Main purpose | Store source code and repositories | Store and share container images |
| Users | Developers and teams | Developers, DevOps engineers, and deployment systems |
| Typical content | Code, commits, pull requests | Docker images, tags, versions |

## Tagging an Image

A tag gives the image a version or label.

```bash
docker tag portfolio-api muniprasad07/portfolio-api:v1
```

This makes the image easier to identify and manage.

## Pushing to Docker Hub

```bash
docker push muniprasad07/portfolio-api:v1
```

This uploads the image to Docker Hub so others can pull it.

## Pulling from Docker Hub

```bash
docker pull muniprasad07/portfolio-api:v1
```

This downloads the image to your local machine.

## Why Docker Says “Layer Already Exists”

Docker often says this when it finds that a layer already exists locally and can be reused.

This is usually a good sign. It means:
- Docker recognized the layer from cache
- it does not need to rebuild everything
- build performance is improved

In short, Docker is being efficient.

## Docker Registry Architecture

A registry is the storage service for images.

```text
Developer Machine --> Docker CLI --> Registry (Docker Hub / private registry)
```

A registry stores image layers and metadata. When you pull an image, Docker downloads the needed layers. When you push, Docker uploads them.

## Docker Hub, Kubernetes, and ECS

These are related but different.

### Docker Hub
- image registry
- stores and distributes container images

### Kubernetes
- container orchestration platform
- manages running containers at scale

### ECS
- Amazon Elastic Container Service
- runs containers in AWS

### Simple comparison
- Docker Hub = where images are stored
- Kubernetes/ECS = where images are run and managed

## Difference Between docker build, docker push, and docker pull

- `docker build`: creates an image from a Dockerfile
- `docker push`: uploads an image to a registry
- `docker pull`: downloads an image from a registry

```text
docker build   -> create image locally
docker push    -> send image to registry
docker pull    -> download image from registry
```

## Day 2 Reflection

Day 2 taught the next essential DevOps skills:
- debugging containers with logs and exec
- managing containers with remove and inspect commands
- sharing images through save/load and Docker Hub
- understanding the image distribution workflow used in real-world delivery pipelines

This completes the bridge between local Docker learning and deployment automation.
