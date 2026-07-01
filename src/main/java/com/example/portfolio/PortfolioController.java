package com.example.portfolio;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse(
                "UP",
                "cloud-devops-portfolio-api",
                "1.0.0",
                OffsetDateTime.now().toString());
    }

    @GetMapping("/profile")
    public ProfileResponse profile() {
        return new ProfileResponse(
                "Cloud DevOps Engineer",
                "A beginner-friendly portfolio API showcasing Java, Spring Boot, Docker, Kubernetes, Jenkins, AWS, and GCP.",
                List.of("Java 17", "Spring Boot 3", "Docker", "Kubernetes", "Jenkins", "AWS", "GCP"),
                Map.of(
                        "github", "https://github.com/your-username",
                        "linkedin", "https://linkedin.com/in/your-profile",
                        "email", "your.email@example.com"));
    }

    @GetMapping("/projects")
    public List<ProjectResponse> projects() {
        return List.of(
                new ProjectResponse(
                        "Cloud DevOps Portfolio API",
                        "Spring Boot REST API packaged with Docker and deployable to Kubernetes.",
                        List.of("Java 17", "Spring Boot 3", "Docker", "Kubernetes", "Jenkins"),
                        "https://github.com/your-username/cloud-devops-portfolio-api"),
                new ProjectResponse(
                        "CI/CD Pipeline Demo",
                        "Example Jenkins pipeline that builds, tests, pushes an image, and deploys to Kubernetes.",
                        List.of("Jenkins", "Docker", "Kubernetes", "Cloud Registry"),
                        "https://github.com/your-username/cicd-pipeline-demo"));
    }

    public record HealthResponse(String status, String service, String version, String timestamp) {
    }

    public record ProfileResponse(
            String role,
            String summary,
            List<String> skills,
            Map<String, String> links) {
    }

    public record ProjectResponse(
            String name,
            String description,
            List<String> technologies,
            String repositoryUrl) {
    }
}
