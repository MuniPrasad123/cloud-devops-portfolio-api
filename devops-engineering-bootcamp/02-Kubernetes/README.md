# Kubernetes Day 1 Learning

Welcome to the Kubernetes learning folder for Day 1.
This folder captures the Day 1 Kubernetes learning journey, environment details, commands, architecture, assignments, and interview preparation.

## Introduction

Kubernetes is a container orchestration platform designed to manage applications across clusters, provide scaling, service discovery, self-healing, and rolling updates.

This Day 1 learning path focuses on the fundamentals of Kubernetes and how it works with Docker Desktop, kind, and `containerd`.

## Prerequisites

- Windows
- Docker Desktop with WSL2 enabled
- Docker Desktop Kubernetes enabled
- `kubectl` installed and configured
- Basic Docker knowledge
- Access to the project repository

## Current environment

- Kubernetes context: `docker-desktop`
- Kubernetes version: `v1.36.1`
- Cluster type: `kind`
- Nodes: 1
- Node name: `desktop-control-plane`
- Node role: `control-plane`
- Container runtime: `containerd`
- Cluster status: Active

## Folder structure

- [Day 1 Notes](./kubernetes-day1-notes.md)
- [Fundamentals](./kubernetes-fundamentals.md)
- [Command Cheat Sheet](./kubernetes-command-cheatsheet.md)
- [Interview Questions](./kubernetes-interview-qa.md)
- [Assignments](./kubernetes-assignments.md)
- [Learning Log](./kubernetes-learning-log.md)
- [Learning Summary](./kubernetes-learning-summary.md)

## Day-by-day roadmap

- Day 1: Kubernetes fundamentals, local cluster, Pod lifecycle, namespaces, labels, and troubleshooting.
- Day 2: YAML manifests, Deployments, Services, scaling, rolling updates, and rollback.
- Day 3: Storage, ConfigMaps, Secrets, networking, and production readiness.
- Future days: EKS, GKE, OpenShift, Helm, monitoring, security, storage, and production deployments.

## Day 1 status

Completed:
- Kubernetes purpose and architecture
- Docker vs Kubernetes vs OpenShift comparison
- Cluster hierarchy
- Control-plane and node components
- Desired state and reconciliation
- First Pod exercise and lifecycle observation
- Pod labels, selectors, namespaces, and troubleshooting
- Local Docker Desktop kind cluster setup

## Commands learned

- `kubectl config current-context`
- `kubectl config get-contexts`
- `kubectl cluster-info`
- `kubectl get nodes`
- `kubectl get nodes -o wide`
- `kubectl get namespaces`
- `kubectl get pods`
- `kubectl get pods -A`
- `kubectl get pods --show-labels`
- `kubectl get pods -l run=nginx`
- `kubectl run nginx --image=nginx`
- `kubectl describe pod nginx`
- `kubectl get pod nginx -o wide`
- `kubectl logs nginx`
- `kubectl logs nginx --previous`
- `kubectl exec -it nginx -- sh`
- `kubectl delete pod nginx`

## Architecture overview

Kubernetes architecture for local learning:

```text
Windows
└── WSL2
    └── Docker Desktop
        └── kind cluster
            └── desktop-control-plane node
                └── nginx Pod
                    └── nginx container
```

The local cluster includes:
- Control plane components: API Server, etcd, Scheduler, Controller Manager
- Node components: kubelet, kube-proxy, container runtime
- Supporting services: CoreDNS, local-path-provisioner, kindnet

## Learning objectives

- Understand why Kubernetes exists
- Understand the Kubernetes vs Docker vs OpenShift relationship
- Learn cluster concepts: node, Pod, Deployment, ReplicaSet, Service
- Learn key control-plane and node components
- Practice Pod creation, inspection, and troubleshooting
- Learn namespaces, labels, selectors, and Pod networking basics
- Build a foundation for YAML manifests and production deployment

## Interview preparation

This folder contains a dedicated interview question file with beginner and architect-level answers.
Use it to prepare for questions on:
- why Kubernetes exists
- container orchestration
- cluster components
- desired state and reconciliation
- Pod lifecycle and failure diagnosis
- labels and selectors
- service discovery and CoreDNS
- kind and OpenShift

## Project-based learning

This Kubernetes learning path is connected to the `cloud-devops-portfolio-api` project. The goal is to apply the same DevOps mindset used in Docker learning to container orchestration and cluster management.

Example project scenarios:
- deploying a Spring Boot microservice with `nginx` as a test application
- creating a checkout service behind a Kubernetes Service
- using labels like `app=checkout`, `team=ecommerce`, and `environment=production`
- troubleshooting logs and Pod health in a local cluster before moving to cloud environments

## Future roadmap

Future topics to cover:
- Amazon EKS and Google GKE
- OpenShift platform and enterprise Kubernetes
- Helm charts and application packaging
- Monitoring with Prometheus and Grafana
- Security with RBAC, Network Policies, and Secrets
- Storage with PersistentVolumes and PersistentVolumeClaims
- Production deployments, observability, and high availability

