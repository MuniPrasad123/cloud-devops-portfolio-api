# Kubernetes Command Cheat Sheet

## Context and cluster information

### Current context
```bash
kubectl config current-context
```
- Purpose: show the active Kubernetes context.
- Production usage: ensure commands run against the correct cluster.
- Expected output example:
```text
docker-desktop
```

### List contexts
```bash
kubectl config get-contexts
```
- Purpose: list available contexts.
- Explanation: each context maps a cluster, user, and namespace.
- Expected output example:
```text
CURRENT   NAME             CLUSTER          AUTHINFO         NAMESPACE
*         docker-desktop   docker-desktop   docker-desktop
```

### Cluster info
```bash
kubectl cluster-info
```
- Purpose: verify cluster services and API access.
- Explanation: shows API server and DNS endpoints.
- Expected output example:
```text
Kubernetes control plane is running at https://127.0.0.1:6443
CoreDNS is running at https://127.0.0.1:6443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
```

## Nodes and namespaces

### Get nodes
```bash
kubectl get nodes
```
- Purpose: list cluster nodes.
- Explanation: shows node status and roles.
- Expected output example:
```text
NAME                  STATUS   ROLES           AGE   VERSION
desktop-control-plane Ready    control-plane   1d    v1.36.1
```

### Get nodes with wide output
```bash
kubectl get nodes -o wide
```
- Purpose: show extra node details.
- Explanation: includes internal IP and OS image.

### Get namespaces
```bash
kubectl get namespaces
```
- Purpose: list cluster namespaces.
- Production usage: inspect resource partitions.
- Expected output example:
```text
NAME              STATUS   AGE
default           Active   1d
kube-system       Active   1d
kube-public       Active   1d
kube-node-lease   Active   1d
local-path-storage Active  1d
```

## Pods and labels

### Get Pods
```bash
kubectl get pods
```
- Purpose: list Pods in the current namespace.
- Expected output example:
```text
NAME    READY   STATUS    RESTARTS   AGE
nginx   1/1     Running   0          2m
```

### Get Pods in all namespaces
```bash
kubectl get pods -A
```
- Purpose: show Pods across the cluster.
- Explanation: useful for troubleshooting system and user Pods.

### Show Pod labels
```bash
kubectl get pods --show-labels
```
- Purpose: display labels associated with Pods.

### Filter Pods by label
```bash
kubectl get pods -l run=nginx
```
- Purpose: select Pods matching the `run=nginx` label.

## Create and inspect a Pod

### Run a Pod
```bash
kubectl run nginx --image=nginx
```
- Purpose: create a standalone Pod named `nginx`.
- Explanation: uses the `nginx` image from Docker Hub.
- Production note: standalone Pods are for learning, not production.

### Describe a Pod
```bash
kubectl describe pod nginx
```
- Purpose: show detailed Pod events and state.
- Explanation: helps understand scheduling, image pulling, and status.

### Get Pod wide output
```bash
kubectl get pod nginx -o wide
```
- Purpose: show Pod IP, node name, and host IP.

## Logs and debugging

### Get current Pod logs
```bash
kubectl logs nginx
```
- Purpose: read the current container logs.
- Explanation: useful for application troubleshooting.

### Get previous Pod logs
```bash
kubectl logs nginx --previous
```
- Purpose: read logs from the previous terminated container.
- Explanation: useful after crash loops or restarts.

### Execute a shell inside a Pod
```bash
kubectl exec -it nginx -- sh
```
- Purpose: run an interactive shell inside the running container.
- Explanation: `-it` attaches a terminal and `--` separates kubectl options from the command.

### Delete a Pod
```bash
kubectl delete pod nginx
```
- Purpose: remove the Pod.
- Explanation: in this Day 1 exercise, the standalone Pod will not be recreated automatically.

## Command words explained

| Word | Meaning |
|---|---|
| `kubectl` | Kubernetes command-line interface |
| `get` | Retrieve resources |
| `describe` | Show detailed resource information |
| `logs` | Print container logs |
| `run` | Create and run a resource |
| `delete` | Remove a resource |
| `exec` | Execute a command in a running Pod |
| `-A` | All namespaces |
| `-o wide` | Extended output with more columns |
| `-l` | Label selector |
| `--image` | Specify the image for the Pod |
| `--previous` | Show logs from the previous container instance |
| `-it` | Interactive terminal mode |
| `--` | Signal the end of options and start of command to run inside the container |

## Production usage notes

- Use `kubectl config current-context` to ensure you are targeting the correct cluster.
- Use `kubectl get nodes` and `kubectl get namespaces` before deploying workloads.
- Use labels and selectors to manage groups of Pods at scale.
- Avoid using standalone Pods for production; use Deployments instead.
- Use centralized logging and monitoring for cluster health and container diagnostics.
