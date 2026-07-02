# Docker Learning Summary

## Project Context

This learning journey uses the Spring Boot project named `cloud-devops-portfolio-api`.

### Project details
- Local image name: `portfolio-api`
- Docker Hub image: `muniprasad07/portfolio-api:v1`
- Container names used: `portfolio-container`, `modest_lamport`
- Application port: `8080`
- API endpoints: `/health`, `/profile`, `/projects`

## What I Learned Day 1

Day 1 focused on Docker fundamentals.

Key ideas:
- Docker solves environment mismatch problems
- Images and containers are different concepts
- Dockerfiles define how images are built
- Docker layers can be cached for faster builds
- Container ports must be mapped correctly

## What I Learned Day 2

Day 2 focused on real-world operational tasks.

Key ideas:
- Use `docker logs` to inspect runtime behavior
- Use `docker exec` to enter and inspect containers
- Use `docker save` and `docker load` to transfer images manually
- Use Docker Hub for image sharing and distribution
- Understand the difference between build, push, and pull

## Mentor Notes

- Day 1 focused on Docker fundamentals and architecture.
- Day 2 focused on debugging, registry usage, Docker Hub push/pull, and the real-world DevOps workflow.
- Day 3 will continue with Docker Compose and multi-container application orchestration.

## Architecture Mindset

From an architect's perspective, Docker is not just a tool for packaging applications. It is a standard way to make software portable, repeatable, and easier to deploy across different environments.

## Portfolio Takeaway

This Docker learning path is now connected to a real project and a practical workflow:
1. Build an image
2. Run a container
3. Verify APIs
4. Push the image to Docker Hub
5. Prepare for deployment and orchestration

## Suggested Next Step

Continue with Docker Compose to run multi-service applications such as:
- application container
- database container
- networking between services
