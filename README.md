# cloud-devops-portfolio-api

> **Author:** Muni Prasad K
>
> **Role:** Senior Software Engineer / Technical Consultant
>
> **GitHub:** https://github.com/MuniPrasad123
>
> **LinkedIn:** https://linkedin.com/in/muni-prasad-k-7600bb105
>
> **Email:** prasad.krish95@gmail.com

---

## 1. Project Overview

`cloud-devops-portfolio-api` is a portfolio-grade Spring Boot application that demonstrates a cloud-native delivery pipeline and infrastructure automation. The repository includes source code, Docker packaging, local orchestration, Kubernetes deployment manifests, and a Jenkins CI/CD pipeline.

## 2. Business Purpose

This project is designed as a technical portfolio asset for showcasing solution design, deployment automation, and cloud readiness. It provides a reference implementation that highlights the engineering approach used to deliver maintainable and scalable services.

## 3. Technical Objective

- Deliver a lightweight Java REST service using Spring Boot.
- Enable repeatable local and cloud deployments.
- Demonstrate containerization, Kubernetes orchestration, and CI/CD automation.
- Support multi-cloud deployment paths for AWS and GCP.
- Provide a clear foundation for discussion in architecture interviews.

## 4. Tech Stack Explanation

| Layer | Technology | Rationale |
|---|---|---|
| Application | Java 17, Spring Boot 3 | Modern, supported runtime and rapid REST development |
| Build | Maven | Standard Java build and dependency management |
| Containerization | Docker | Consistent packaging and runtime environment |
| Local Orchestration | Docker Compose | Simple multi-container development flow |
| Orchestration | Kubernetes | Production-grade deployment and scaling |
| CI/CD | Jenkins | Automation for build, test, image publish, and deployment |
| Cloud | AWS ECS/ECR, GCP GKE/Artifact Registry | Industry-standard multi-cloud deployment options |

## 5. Folder Structure Explanation

```text
cloud-devops-portfolio-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/portfolio/
│   │   │   ├── CloudDevopsPortfolioApiApplication.java
│   │   │   └── PortfolioController.java
│   │   └── resources/application.yml
│   └── test/java/com/example/portfolio/
│       └── PortfolioControllerTest.java
├── k8s/
│   ├── deployment.yaml
│   └── service.yaml
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
├── pom.xml
└── README.md
```

- `src/main/java`: Core service implementation.
- `src/main/resources`: Spring Boot configuration and management endpoints.
- `src/test/java`: automated unit and integration tests.
- `k8s/`: Kubernetes deployment definitions.
- `Dockerfile`: container build pipeline.
- `docker-compose.yml`: local container orchestration.
- `Jenkinsfile`: CI/CD pipeline script.
- `pom.xml`: project build and dependency metadata.

## 6. Solution Architecture

The solution is organized into a simple API service with separate build, packaging, and deployment responsibilities.

```text
Client
  |
  v
Spring Boot API
  |
  +--> HTTP endpoints (/health, /profile, /projects)
  |
  +--> Runtime config via application.yml
  |
  +--> Tests via PortfolioControllerTest
  |
  v
Docker image
  |
  +--> Local execution
  +--> Kubernetes deployment
  +--> CI/CD pipeline with Jenkins
```

### Architectural Principles

- Separation of concerns between application logic and deployment configuration.
- Use of container best practices with multi-stage builds.
- Resilient deployment with Kubernetes readiness and liveness checks.
- Credential management outside the source tree.

## 7. Logical Architecture

- **Presentation Layer**: REST controller that exposes API endpoints.
- **Data Model**: Java records for API response payloads.
- **Configuration**: `application.yml` for Spring Boot application metadata and management endpoints.
- **Testing**: `PortfolioControllerTest` validates the service contract.

### Component summary

- `CloudDevopsPortfolioApiApplication.java`: Spring Boot application entry point.
- `PortfolioController.java`: REST controller with three endpoints.
- `application.yml`: service name, server port, and management endpoint exposure.
- `PortfolioControllerTest.java`: endpoint validation using MockMvc.

## 8. Deployment Architecture

This project supports deployment from source code to a running service across multiple environments.

- Local: Maven, Docker, Docker Compose
- Container: Docker image built with a multi-stage Dockerfile
- Cluster: Kubernetes Deployment and Service definitions
- CI/CD: Jenkins pipeline automates build, test, publish, and deploy
- Cloud: Optional AWS ECS/ECR and GCP GKE/Artifact Registry workflows

### Deployment flow

```text
Source code -> Maven build -> Docker image -> Registry -> Kubernetes/ECS -> Running service
```

## 9. API Documentation with Sample Requests and Responses

### GET `/health`

**Request**:

```powershell
curl http://localhost:8080/health
```

**Response**:

```json
{
  "status": "UP",
  "service": "cloud-devops-portfolio-api",
  "version": "1.0.0",
  "timestamp": "2026-07-01T12:00:00+05:30"
}
```

### GET `/profile`

**Request**:

```powershell
curl http://localhost:8080/profile
```

**Response**:

```json
{
  "role": "Cloud DevOps Engineer",
  "summary": "A beginner-friendly portfolio API showcasing Java, Spring Boot, Docker, Kubernetes, Jenkins, AWS, and GCP.",
  "skills": [
    "Java 17",
    "Spring Boot 3",
    "Docker",
    "Kubernetes",
    "Jenkins",
    "AWS",
    "GCP"
  ],
  "links": {
    "github": "https://github.com/your-username",
    "linkedin": "https://linkedin.com/in/your-profile",
    "email": "your.email@example.com"
  }
}
```

### GET `/projects`

**Request**:

```powershell
curl http://localhost:8080/projects
```

**Response**:

```json
[
  {
    "name": "Cloud DevOps Portfolio API",
    "description": "Spring Boot REST API packaged with Docker and deployable to Kubernetes.",
    "technologies": [
      "Java 17",
      "Spring Boot 3",
      "Docker",
      "Kubernetes",
      "Jenkins"
    ],
    "repositoryUrl": "https://github.com/your-username/cloud-devops-portfolio-api"
  },
  {
    "name": "CI/CD Pipeline Demo",
    "description": "Example Jenkins pipeline that builds, tests, pushes an image, and deploys to Kubernetes.",
    "technologies": [
      "Jenkins",
      "Docker",
      "Kubernetes",
      "Cloud Registry"
    ],
    "repositoryUrl": "https://github.com/your-username/cicd-pipeline-demo"
  }
]
```

> Note: the sample response uses placeholder links. Update the payload and project metadata as needed for production.

## 10. Local Setup Steps

### Prerequisites

- Java 17 JDK
- Maven 3.9+
- Docker Desktop (optional)
- Git

### Setup commands (Windows)

```powershell
cd "C:\Users\Hp\Documents\Devop's Practise\cloud-devops-portfolio-api"
```

### Run locally

```powershell
mvn clean test
mvn spring-boot:run
```

### Verify endpoints

```powershell
curl http://localhost:8080/health
curl http://localhost:8080/profile
curl http://localhost:8080/projects
```

## 11. Maven Build and Test Steps

### Build package

```powershell
mvn clean package
```

### Run tests

```powershell
mvn test
```

### Build notes

- `pom.xml` uses Spring Boot starter dependencies.
- The Spring Boot Maven plugin packages the project as an executable jar.

## 12. Docker Build and Run Steps

### Build Docker image

```powershell
docker build -t cloud-devops-portfolio-api:local .
```

### Run Docker container

```powershell
docker run --rm -p 8080:8080 cloud-devops-portfolio-api:local
```

### Validate service

```powershell
curl http://localhost:8080/health
```

### Dockerfile insights

- Multi-stage build separates build-time and runtime dependencies.
- Runtime image uses `eclipse-temurin:17-jre-alpine`.
- Container runs as a non-root user `spring`.

## 13. Docker Compose Steps

### Start the application

```powershell
docker compose up --build
```

### Stop the application

```powershell
docker compose down
```

### Health check

The Compose definition includes a health check for `/health`.

## 14. Kubernetes Deployment Steps

### Prerequisites

- Kubernetes cluster or local Kubernetes runtime
- `kubectl` configured
- Image accessible to the cluster

### Deploy to Kubernetes

```powershell
kubectl apply -f k8s/
kubectl get pods
kubectl get service cloud-devops-portfolio-api
```

### Port-forward access

```powershell
kubectl port-forward service/cloud-devops-portfolio-api 8080:80
curl http://localhost:8080/health
```

### Manifest details

- `k8s/deployment.yaml`: 2 replicas, readiness and liveness probes, resource requests/limits.
- `k8s/service.yaml`: LoadBalancer service exposing port 80 to container port 8080.

## 15. Jenkins CI/CD Pipeline Explanation

The `Jenkinsfile` defines a pipeline with these stages:

1. **Checkout**: retrieve source code from SCM.
2. **Build**: compile and package the application.
3. **Test**: execute automated tests.
4. **Docker Build**: build and tag Docker images.
5. **Docker Push**: push image versions to a container registry.
6. **Deploy**: update Kubernetes deployment and monitor rollout.

### Jenkins credentials

- `docker-registry-credentials`: username/password for Docker registry login.
- `kubeconfig`: kubeconfig file for Kubernetes access.

### Pipeline design

- Uses environment variables for image names and registry details.
- Leverages Docker login and `kubectl` from the build agent.
- Cleans the workspace after every execution.

## 16. AWS ECS + ECR Deployment Flow

### Deployment flow

```text
Build -> Docker image -> ECR push -> ECS task definition -> ECS service -> ALB -> /health
```

### AWS deployment commands (Windows PowerShell)

```powershell
aws ecr create-repository --repository-name cloud-devops-portfolio-api
$env:AWS_REGION = 'us-east-1'
$env:AWS_ACCOUNT_ID = '123456789012'
$env:ECR_REPOSITORY = 'cloud-devops-portfolio-api'
$env:IMAGE_URI = "$env:AWS_ACCOUNT_ID.dkr.ecr.$env:AWS_REGION.amazonaws.com/$env:ECR_REPOSITORY:latest"
aws ecr get-login-password --region $env:AWS_REGION | docker login --username AWS --password-stdin "$env:AWS_ACCOUNT_ID.dkr.ecr.$env:AWS_REGION.amazonaws.com"
docker build -t $env:IMAGE_URI .
docker push $env:IMAGE_URI
```

> Replace AWS account values with your own production or sandbox account.

### ECS considerations

- Use Fargate for serverless compute.
- Configure a health check on `/health`.
- Attach an Application Load Balancer for public access.

## 17. GCP GKE + Artifact Registry Deployment Flow

### Deployment flow

```text
Build -> Artifact Registry push -> GKE cluster -> Kubernetes deployment -> service exposure
```

### GCP deployment commands (Windows PowerShell)

```powershell
$env:GCP_PROJECT_ID = 'your-project-id'
$env:GCP_REGION = 'us-central1'
$env:GAR_REPOSITORY = 'portfolio-apps'
$env:IMAGE_URI = "$env:GCP_REGION-docker.pkg.dev/$env:GCP_PROJECT_ID/$env:GAR_REPOSITORY/cloud-devops-portfolio-api:latest"
gcloud artifacts repositories create $env:GAR_REPOSITORY --repository-format=docker --location=$env:GCP_REGION
gcloud auth configure-docker "$env:GCP_REGION-docker.pkg.dev"
docker build -t $env:IMAGE_URI .
docker push $env:IMAGE_URI
gcloud container clusters create portfolio-cluster --region=$env:GCP_REGION --num-nodes=2
gcloud container clusters get-credentials portfolio-cluster --region=$env:GCP_REGION
kubectl apply -f k8s/
```

### GKE considerations

- Use Artifact Registry for private image storage.
- Provision the cluster in the same region as the registry.
- Use `kubectl` after `gcloud container clusters get-credentials`.

## 18. Security and Production Best Practices

- Do not commit credentials or kubeconfig files to source control.
- Store Docker credentials in Jenkins credentials vault.
- Use least privileged IAM roles for cloud registry and cluster access.
- Apply Kubernetes resource requests and limits.
- Enable health probes for automatic container recovery.
- Use secure base images and scan container images.
- Replace generic placeholder portfolio links with actual values before publishing.

## 19. Troubleshooting Guide

### Docker troubleshooting

```powershell
docker ps
docker logs cloud-devops-portfolio-api
docker inspect cloud-devops-portfolio-api
```

### Kubernetes troubleshooting

```powershell
kubectl get pods
kubectl describe pod -l app=cloud-devops-portfolio-api
kubectl logs deployment/cloud-devops-portfolio-api
kubectl rollout status deployment/cloud-devops-portfolio-api
```

### Jenkins troubleshooting

- Confirm pipeline node has Java 17, Maven, Docker, and `kubectl`.
- Validate Jenkins credentials IDs match the `Jenkinsfile` definitions.
- Inspect pipeline console output for shell command failures.

## 20. Learning Outcomes

This project teaches how to:

- Design a service-level architecture for cloud-native applications.
- Build and package Java applications with Maven.
- Containerize applications with Docker.
- Orchestrate workloads in Kubernetes with readiness and liveness probes.
- Implement CI/CD with Jenkins.
- Prepare deployments for AWS ECS/ECR and GCP GKE/Artifact Registry.

## 21. Future Enhancements

- Add a persistence layer with a database.
- Add authentication and authorization.
- Implement integration and contract tests.
- Add metrics and logging with Prometheus/Grafana.
- Add Horizontal Pod Autoscaling to Kubernetes manifests.
- Add infrastructure-as-code for cloud provisioning.

## How to Practice This Project in 1–2 Days

### Day 1

- Review the source code and project structure.
- Run the application locally with Maven.
- Build the Docker image and validate the container.
- Start the service with Docker Compose.

### Day 2

- Deploy the application to Kubernetes.
- Review the Jenkins pipeline and simulate a CI/CD run.
- Explore AWS ECS/ECR or GCP GKE deployment paths.
- Document the architecture and identify improvement areas.

## Portfolio Summary

`cloud-devops-portfolio-api` is a professional reference project built to demonstrate modern application delivery and cloud deployment patterns. It provides a clear example of how to design, package, deploy, and automate a Java service with Docker, Kubernetes, and Jenkins. This repository is suitable for technical interviews and architecture reviews.

---

## Screenshots (placeholders)

- Docker running: `./screenshots/docker-running.png`
- Kubernetes pods: `./screenshots/k8s-pods.png`
- Jenkins pipeline success: `./screenshots/jenkins-success.png`
- AWS ECS service: `./screenshots/aws-ecs-service.png`
- GCP GKE workload: `./screenshots/gcp-gke-workload.png`

```bash
export AWS_REGION=us-east-1
export AWS_ACCOUNT_ID=123456789012
export ECR_REPOSITORY=cloud-devops-portfolio-api
export IMAGE_URI=$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPOSITORY:latest
```

Login to ECR:

```bash
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
```

Build and push:

```bash
docker build -t $IMAGE_URI .
docker push $IMAGE_URI
```

ECS deployment outline:

1. Create an ECS cluster.
2. Create a task definition using the pushed image URI.
3. Expose container port `8080`.
4. Create an ECS service using AWS Fargate.
5. Attach an Application Load Balancer if the API should be publicly reachable.
6. Set health check path to `/health`.

## Deploy To GCP GKE With Artifact Registry

Prerequisites:

- Google Cloud CLI installed and authenticated
- Docker installed
- A GCP project with Artifact Registry and GKE enabled

Set variables:

```bash
export GCP_PROJECT_ID=your-project-id
export GCP_REGION=us-central1
export GAR_REPOSITORY=portfolio-apps
export IMAGE_URI=$GCP_REGION-docker.pkg.dev/$GCP_PROJECT_ID/$GAR_REPOSITORY/cloud-devops-portfolio-api:latest
```

Create an Artifact Registry repository:

```bash
gcloud artifacts repositories create $GAR_REPOSITORY \
  --repository-format=docker \
  --location=$GCP_REGION
```

Authenticate Docker:

```bash
gcloud auth configure-docker $GCP_REGION-docker.pkg.dev
```

Build and push:

```bash
docker build -t $IMAGE_URI .
docker push $IMAGE_URI
```

Create or connect to a GKE cluster:

```bash
gcloud container clusters create portfolio-cluster \
  --region=$GCP_REGION \
  --num-nodes=2

gcloud container clusters get-credentials portfolio-cluster \
  --region=$GCP_REGION
```

Update `k8s/deployment.yaml` with the Artifact Registry image:

```yaml
image: us-central1-docker.pkg.dev/your-project-id/portfolio-apps/cloud-devops-portfolio-api:latest
```

Deploy:

```bash
kubectl apply -f k8s/
kubectl get service cloud-devops-portfolio-api
```

## Jenkins CI/CD

The included `Jenkinsfile` has these stages:

- Checkout
- Build
- Test
- Docker Build
- Docker Push
- Deploy

Jenkins prerequisites:

- Jenkins agent with Java 17, Maven, Docker, and `kubectl`
- Docker registry credentials stored in Jenkins as `docker-registry-credentials`
- Kubernetes kubeconfig stored as a Jenkins secret file with ID `kubeconfig`
- Jenkins user allowed to run Docker commands

Pipeline setup:

1. Push this project to a Git repository.
2. Create a Jenkins Pipeline job.
3. Point the job to the Git repository.
4. Keep the pipeline script path as `Jenkinsfile`.
5. Update `DOCKER_REGISTRY` in `Jenkinsfile`.
6. Update `k8s/deployment.yaml` if your Kubernetes registry path is different.
7. Run the pipeline.

## Production-Style Notes

- The Dockerfile uses a multi-stage build to keep the runtime image smaller.
- The container runs as a non-root user.
- Kubernetes manifests include resource requests, limits, liveness probes, and readiness probes.
- Tests cover the public API endpoints.
- Registry names and profile links are placeholders so beginners can safely replace them.

## Common Troubleshooting

Check application logs:

```bash
docker logs cloud-devops-portfolio-api
kubectl logs deployment/cloud-devops-portfolio-api
```

Check Kubernetes rollout:

```bash
kubectl rollout status deployment/cloud-devops-portfolio-api
kubectl describe deployment cloud-devops-portfolio-api
```

Check service access:

```bash
kubectl get service cloud-devops-portfolio-api
kubectl port-forward service/cloud-devops-portfolio-api 8080:80
```
