# Docker Command Cheat Sheet

## Build and Run

```bash
docker build -t portfolio-api .
docker run -d --name portfolio-container -p 8080:8080 portfolio-api
```

## Container Management

```bash
docker ps
docker ps -a
docker stop portfolio-container
docker start portfolio-container
docker rm portfolio-container
docker rm -f portfolio-container
```

## Logs and Debugging

```bash
docker logs portfolio-container
docker logs -f portfolio-container
docker exec -it portfolio-container sh
```

## Image Management

```bash
docker images
docker tag portfolio-api muniprasad07/portfolio-api:v1
docker push muniprasad07/portfolio-api:v1
docker pull muniprasad07/portfolio-api:v1
docker save -o portfolio-api.tar portfolio-api
docker load -i portfolio-api.tar
```

## Useful Notes

- Use `docker ps` to see running containers.
- Use `docker ps -a` to see stopped containers too.
- Use `docker logs` when the app is failing or you want runtime output.
- Use `docker exec -it ... sh` when you need to inspect the container filesystem.
- Use `docker rm` to delete old containers and avoid name conflicts.
- Use `docker tag` before pushing an image to a registry.

## Common Example for This Project

```bash
# Build image
docker build -t portfolio-api .

# Run container
 docker run -d --name portfolio-container -p 8080:8080 portfolio-api

# Test endpoints
curl http://localhost:8080/health
curl http://localhost:8080/profile
curl http://localhost:8080/projects
```

## Name Conflict Example

```bash
docker rm -f portfolio-container
docker run -d --name modest_lamport -p 8080:8080 portfolio-api
```
