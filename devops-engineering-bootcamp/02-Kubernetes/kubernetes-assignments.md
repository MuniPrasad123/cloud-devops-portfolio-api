# Kubernetes Day 1 Assignments

These assignments reinforce Day 1 concepts with practical exercises and reflective learning.

## Assignment 1: Explain Docker vs Kubernetes

Write a short paragraph in your own words that explains the difference between Docker and Kubernetes.

### What to include
- Docker builds images and runs containers.
- Kubernetes schedules container workloads and manages their lifecycle.
- Why both are needed in a modern container-based application.

## Assignment 2: Draw the Kubernetes hierarchy

Draw the following structure on paper or using a diagram tool:

```text
Cluster
→ Node
→ Pod
→ Container
```

### What to include
- The role of a cluster
- The role of a node
- What a Pod represents
- What a container is inside a Pod

## Assignment 3: Explain request flow

Describe the request flow from `kubectl` to `containerd`.

### What to include
- `kubectl` calls the API Server
- API Server stores desired state in `etcd`
- Controller observes desired state
- Scheduler selects a node
- `kubelet` receives instructions
- `containerd` pulls the image and starts the container

## Assignment 4: Create an nginx Pod and inspect it

Run:

```bash
kubectl run nginx --image=nginx
```

Then inspect it using:

```bash
kubectl get pod nginx
kubectl describe pod nginx
kubectl get pod nginx -o wide
kubectl get pods --show-labels
```

### What to observe
- Pod status
- Pod IP and node name
- Labels attached to the Pod
- Events and container state

## Assignment 5: View labels and use a selector

Practice these commands:

```bash
kubectl get pods --show-labels
kubectl get pods -l run=nginx
```

### What to explain
- How the label `run=nginx` is attached to the Pod
- How the selector filters Pods
- Why labels and selectors are important for Services and Deployments

## Assignment 6: Delete the standalone nginx Pod

Run:

```bash
kubectl delete pod nginx
```

### What to observe
- That the standalone Pod is removed
- That Kubernetes does not recreate it automatically
- Why a Deployment-managed Pod behaves differently

## Assignment 7: Identify component responsibilities

Explain the responsibilities of these Kubernetes components:
- API Server
- etcd
- Scheduler
- Controller Manager
- kubelet
- kube-proxy
- CoreDNS
- containerd

### Suggested answers
- API Server: cluster API entrypoint
- etcd: cluster state store
- Scheduler: selects nodes for Pods
- Controller Manager: runs reconciliation controllers
- kubelet: node agent that manages Pods
- kube-proxy: Service networking rules
- CoreDNS: DNS for Services
- containerd: container runtime

## Assignment 8: Explain RCA after a container crash

Describe the steps for troubleshooting a failed Pod.

### Suggested topics
- `kubectl describe pod <name>` for events and state
- `kubectl logs <name>` for current logs
- `kubectl logs <name> --previous` for prior container logs
- `kubectl get events` for cluster events
- Understanding `CrashLoopBackOff`, `OOMKilled`, and exit codes
- Why centralized logging is important in production

## Bonus assignment: Namespace review

List the namespaces you saw in the cluster and explain the role of each:
- `default`
- `kube-system`
- `kube-public`
- `kube-node-lease`
- `local-path-storage`

## Notes

These exercises are designed to help you move from theory to practice. Save command output, observations, and any questions in your learning log.
