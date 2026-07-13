# Kubernetes Learning Summary

## Completed on Day 1

- Learned why Kubernetes exists and how it complements Docker.
- Understood the difference between Docker, Kubernetes, and OpenShift.
- Reviewed Kubernetes hierarchy: Cluster → Node → Pod → Container.
- Learned about Deployment → ReplicaSet → Pod.
- Explored cluster architecture and control-plane components.
- Studied node components and the container runtime.
- Practiced `kubectl` commands and cluster discovery.
- Experienced a first nginx Pod lifecycle.
- Investigated Pod conditions, status, and logs.
- Learned why standalone Pods are not automatically recreated.
- Practiced troubleshooting with `kubectl describe`, `logs`, and `events`.
- Understood namespaces and their purpose.
- Learned labels and selectors for Pod grouping.
- Reviewed Pod networking basics and Service discovery concepts.
- Confirmed Docker Desktop Kubernetes setup with `kind` and `containerd`.
- Added production-minded notes on centralized logging and monitoring.

## What was learned in detail

- Docker packages applications.
- Kubernetes orchestrates containers.
- OpenShift is an enterprise Kubernetes platform.
- A cluster contains nodes. A Pod contains containers.
- The API Server is the Kubernetes gateway.
- `etcd` stores desired state.
- The scheduler selects a node for Pods.
- The controller manager maintains state.
- `kubelet` manages Pods on each node.
- `kube-proxy` handles Service networking.
- `containerd` runs containers in the node.
- CoreDNS handles DNS-based service discovery.
- Desired state and actual state are continuously reconciled.
- Self-healing replaces failing Pods.
- Standalone Pods are not ideal for production.
- Labels are identity tags; selectors are search conditions.
- Pod IPs are temporary; Services provide stable access.

## Still to learn on Day 2

- YAML manifests and declarative configuration.
- `apiVersion`, `kind`, `metadata`, and `spec` fields.
- ReplicaSet behavior in YAML.
- Deployment workflows and scaling.
- Self-healing in practice with controllers.
- Rolling updates and rollbacks.
- Deployment history and revision management.
- Using labels and selectors in Deployment YAML.
- Creating Services for stable networking.
- Configuring resource requests and limits.
- Applying best practices for production deployments.

## Portfolio-ready takeaway

Day 1 established a clear foundation for Kubernetes:
- understanding why orchestration is needed
- running a Kubernetes cluster locally
- creating and troubleshooting Pods
- learning how Kubernetes maintains desired state
- preparing for manifest-driven deployments and real production scenarios
