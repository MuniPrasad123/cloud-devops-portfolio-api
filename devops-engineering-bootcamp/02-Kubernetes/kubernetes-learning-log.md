# Kubernetes Learning Log

Use this log to capture notes, commands, experiments, and observations from Day 1.

## Day 1 experiments

- Created a local Kubernetes cluster with Docker Desktop and kind.
- Verified Kubernetes context: `docker-desktop`.
- Checked cluster info with `kubectl cluster-info`.
- Ran a standalone Pod: `kubectl run nginx --image=nginx`.
- Inspected Pod details with `kubectl describe pod nginx`.
- Reviewed Pod labels with `kubectl get pods --show-labels`.
- Observed Pod IP and node assignment using `kubectl get pod nginx -o wide`.
- Checked current logs: `kubectl logs nginx`.
- Practiced `kubectl exec -it nginx -- sh` to access the container shell.
- Deleted the standalone Pod and confirmed it was not recreated.

## Observed behavior

- The local cluster runs a single node named `desktop-control-plane`.
- The node is both control-plane and worker.
- The container runtime is `containerd`.
- The architecture includes Windows, WSL2, Docker Desktop, kind cluster, and a Pod.
- The `default` namespace is used for the standalone Pod.
- Pod IPs are ephemeral and may change after recreation.
- Labels are helpful for selecting objects and organizing workloads.

## Commands used

```bash
kubectl config current-context
kubectl config get-contexts
kubectl cluster-info
kubectl get nodes
kubectl get nodes -o wide
kubectl get namespaces
kubectl run nginx --image=nginx
kubectl get pods --show-labels
kubectl describe pod nginx
kubectl logs nginx
kubectl exec -it nginx -- sh
kubectl delete pod nginx
```

## Questions to explore next

- How do I create Kubernetes YAML manifests?
- How are Deployments and Services defined in YAML?
- What is the difference between `Pod` and `Deployment` in manifest form?
- How do I expose a Pod or Deployment to traffic using a Service?
- How does `kubectl apply` differ from `kubectl create`?
- What are resource requests and limits?
- What is a horizontal Pod autoscaler?
