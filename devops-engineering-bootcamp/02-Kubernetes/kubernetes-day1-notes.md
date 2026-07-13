# Kubernetes Day 1 Notes

## 1. Why Kubernetes Exists

Kubernetes exists because running containers with Docker alone becomes difficult at production scale.

Docker is excellent at building images and running individual containers, but it does not solve all operational problems:
- Manual recovery when a container crashes
- Managing hundreds or thousands of containers
- Scaling applications up or down automatically
- Load balancing traffic between replicas
- Service discovery for dynamic workloads
- Rolling updates without downtime
- High availability across multiple machines
- Scheduling workloads across multiple servers
- Self-healing failed containers and Pods

Docker packages and runs containers.
Kubernetes orchestrates and manages containerized workloads.
Kubernetes is not a replacement for Docker. They work together at different layers.
Modern Kubernetes commonly uses `containerd` as the container runtime.

### Developer workflow

```text
Developer
  → Git commit
  → CI/CD pipeline
  → Docker build
  → Container image
  → Image registry
  → Kubernetes Deployment
  → Pods
  → Containers
```

## 2. Docker vs Kubernetes vs OpenShift

| Platform | Primary Role | What it does | Typical use case |
|---|---|---|---|
| Docker | Container development | Builds images, runs containers, pushes images | Local development and image packaging |
| Kubernetes | Container orchestration | Schedules workloads, manages Pods, scales apps, self-heals, rolling updates, service discovery, load balances | Production orchestration across many nodes |
| OpenShift | Enterprise Kubernetes | Kubernetes plus security, developer console, integrated registry, pipelines, monitoring, routes, multi-tenancy | Enterprise platform for regulated workloads |

Memory line:
- Docker packages applications.
- Kubernetes orchestrates containers.
- OpenShift provides an enterprise Kubernetes platform.

## 3. Kubernetes Hierarchy

```text
Cluster
└── Node
    └── Pod
        └── Container
```

Production workload hierarchy:

```text
Deployment
└── ReplicaSet
    └── Pod
        └── Container
```

### Key concepts

- **Cluster**: A set of machines working together to run Kubernetes workloads.
- **Control plane**: Components that manage the cluster state.
- **Worker node**: A machine that runs application Pods.
- **Node**: Either a control plane node or worker node.
- **Pod**: The smallest deployable unit in Kubernetes, one or more containers sharing networking and storage.
- **Container**: A runtime instance managed by the node's container runtime.
- **Namespace**: A logical partition inside the cluster.
- **Deployment**: A controller that manages ReplicaSets and Pods declaratively.
- **ReplicaSet**: Ensures a specific number of Pod replicas are running.
- **Service**: Provides stable networking and discovery for Pods.

## 4. Kubernetes Cluster Architecture

### Control-plane components

- **kube-apiserver**: The front door for all Kubernetes API requests.
- **etcd**: The cluster's key-value store for desired state and configuration.
- **kube-scheduler**: Assigns Pods to nodes based on available capacity and policy.
- **kube-controller-manager**: Runs controllers that maintain cluster state.

### Node components

- **kubelet**: Agent that runs on each node and ensures Pods are running.
- **kube-proxy**: Manages networking rules for Services and Pod traffic.
- **container runtime**: e.g. `containerd`, creates and runs containers.
- **CNI networking plugin**: Provides Pod networking across the cluster.

### Supporting components in the local cluster

- **CoreDNS**: DNS service for Kubernetes Service discovery.
- **local-path-provisioner**: Provides local storage provisioning in development clusters.
- **kindnet**: Network plugin used by kind clusters.

### Architecture request flow

```text
kubectl
→ API Server
→ etcd stores desired state
→ Controller observes desired state
→ Scheduler selects a node
→ kubelet receives instructions
→ containerd pulls the image
→ container starts inside a Pod
```

## 5. Desired State and Actual State

Kubernetes is declarative. You tell Kubernetes what you want, not how to do it.

Example:
- Desired replicas = 3
- Actual replicas = 2

Controller action:
- Create one replacement Pod.

### Important concepts

- **Reconciliation loop**: Kubernetes continuously compares desired state with actual state and acts to correct differences.
- **Desired state**: The configuration you declare.
- **Actual state**: What is currently running.
- **Self-healing**: Kubernetes replaces failed Pods automatically.
- **Immutable infrastructure**: Containers are replaced rather than changed in place.
- **Pets vs cattle**: Pods are cattle. They are disposable and replaceable.

### Standalone Pod vs Deployment-managed Pod

A standalone Pod created with:

```bash
kubectl run nginx --image=nginx
```

is not automatically recreated after deletion because it is not controlled by a Deployment or ReplicaSet.

A Deployment-managed Pod is recreated because the Deployment and ReplicaSet continuously maintain the requested replica count.

## 6. Pod Monitoring and Failure Detection

Kubernetes detects a crashed container through the container runtime.

- The container runtime detects container exit.
- The kubelet observes the container state.
- The kubelet updates Pod status through the API Server.
- Controllers compare actual state with desired state.
- A managed workload can create a replacement Pod.

There is no single fixed monitoring interval. Kubernetes uses:
- runtime events
- kubelet synchronization
- health probes
- control loops

### Failure scenarios

- **Container restart**: the runtime attempts to restart a container in place.
- **Pod replacement**: controllers create a new Pod when the existing Pod is gone.
- **Node failure**: the node is marked NotReady and Pods are rescheduled.
- **CrashLoopBackOff**: a container keeps failing rapidly.
- **OOMKilled**: the kernel terminates a process due to out-of-memory.
- **Exit code 137**: an OOM or forced termination signal.

## 7. RCA and Troubleshooting Failed Pods

### Commands

```bash
kubectl describe pod nginx
kubectl logs nginx
kubectl logs nginx --previous
kubectl get pod nginx -o wide
kubectl get events
kubectl get events --sort-by=.metadata.creationTimestamp
```

### What each command shows

- `kubectl describe pod nginx`: events, state, restart counts, exit reasons, image details, scheduling results, conditions.
- `kubectl logs nginx`: current container logs.
- `kubectl logs nginx --previous`: logs from the previous terminated container.
- `kubectl get pod nginx -o wide`: node assignment, Pod IP, host IP, and more details.
- `kubectl get events`: cluster events such as scheduling and image pulls.
- `kubectl get events --sort-by=.metadata.creationTimestamp`: newest events first.

### Production logging

Logs should be forwarded to a centralized platform:
- Splunk
- ELK
- Grafana Loki
- Datadog
- Sumo Logic
- Fluent Bit
- Fluentd

Logs may be lost when Pods are deleted unless centralized logging is configured.

## 8. Docker Desktop Kubernetes Setup

Environment used:
- Windows
- Docker Desktop
- WSL2
- `kubectl`
- Docker Desktop Kubernetes
- Kubernetes context: `docker-desktop`
- Kubernetes version: `v1.36.1`
- Cluster type: `kind`
- Nodes: 1
- Node name: `desktop-control-plane`
- Node role: `control-plane`
- Container runtime: `containerd`
- Cluster status: Active

Docker Desktop created a local single-node Kubernetes cluster.

This node performs both:
- control-plane role
- workload execution role for local learning

### What is kind?

`kind` means Kubernetes IN Docker.
The Kubernetes node runs inside a Docker container managed by Docker Desktop.

## 9. Commands Practised

### Context and cluster info

```bash
kubectl config current-context
kubectl config get-contexts
kubectl cluster-info
```

### Nodes and namespaces

```bash
kubectl get nodes
kubectl get nodes -o wide
kubectl get namespaces
```

### Pods and labels

```bash
kubectl get pods
kubectl get pods -A
kubectl get pods --show-labels
kubectl get pods -l run=nginx
kubectl run nginx --image=nginx
kubectl describe pod nginx
kubectl get pod nginx -o wide
kubectl logs nginx
kubectl logs nginx --previous
kubectl exec -it nginx -- sh
kubectl delete pod nginx
```

### Command words explained

- `kubectl`: Kubernetes command-line tool.
- `get`: display resources.
- `describe`: show detailed resource information.
- `logs`: show container logs.
- `run`: create and run a resource.
- `delete`: remove a resource.
- `exec`: execute a command in a running Pod.
- `-A`: all namespaces.
- `-o wide`: extended output.
- `-l`: label selector.
- `--image`: image to use.
- `--previous`: previous terminated container logs.
- `-it`: interactive terminal.
- `--`: end of kubectl options and start of container command.

## 10. First Pod Exercise

The command:

```bash
kubectl run nginx --image=nginx
```

created:
- Pod: `nginx`
- Container: `nginx`
- Image: `docker.io/library/nginx`
- Namespace: `default`
- Node: `desktop-control-plane`
- Pod IP: `10.244.0.5`
- Container runtime: `containerd`
- Restart count: `0`
- Status: `Running`
- Ready: `True`

### Lifecycle events observed

- Scheduled
- Pulling
- Pulled
- Created
- Started

### Observed architecture

```text
Windows
└── WSL2 Linux environment
    └── Docker Desktop
        └── kind Kubernetes cluster
            └── desktop-control-plane node
                └── nginx Pod
                    └── nginx container
```

### Node IP vs Pod IP

- Node IP: `172.18.0.2`
- Pod IP: `10.244.0.5`

Pod IPs are temporary and may change when Pods are recreated.

## 11. Namespaces

Namespaces observed:
- `default`
- `kube-system`
- `kube-public`
- `kube-node-lease`
- `local-path-storage`

### What each namespace means

- **default**: user workloads created without an explicit namespace.
- **kube-system**: Kubernetes control-plane and infrastructure workloads.
- **kube-public**: public cluster information readable by all users.
- **kube-node-lease**: node heartbeat lease objects for node health.
- **local-path-storage**: local storage provisioner resources in Docker Desktop.

### Namespace comparisons

Namespaces are like:
- Oracle schema
- Java package
- Linux folder
- Logical Kubernetes partition

Namespaces provide organization, but they are not complete security boundaries on their own.

## 12. Labels and Selectors

The nginx Pod had the label:

```text
run=nginx
```

Commands used:

```bash
kubectl get pods --show-labels
kubectl get pods -l run=nginx
```

### Why labels and selectors matter

- Labels identify Kubernetes objects.
- Selectors find matching objects.
- Deployments use selectors to manage Pods.
- Services use selectors to route traffic to Pods.

Memory line:
- Labels are identity tags.
- Selectors are search conditions.

### Production labels examples

- `app=checkout`
- `environment=production`
- `team=ecommerce`
- `version=v1`

### Example YAML label block

```yaml
metadata:
  labels:
    app: checkout
    environment: production
```

## 13. Pod Networking Basics

- Every Pod receives its own IP.
- Containers inside the same Pod share the same network namespace.
- Containers in the same Pod communicate through `localhost`.
- Pods communicate over the cluster network.
- Pod IPs are not stable.
- Applications should not depend directly on Pod IPs.
- Kubernetes Services provide stable networking and service discovery.
- CoreDNS resolves Kubernetes Service names.

Example request flow:

```text
checkout Pod
→ http://pricing-service
→ Kubernetes Service
→ matching pricing Pods
```

Services provide stable names and load balancing, and they will be covered later.

## 14. Pod Conditions and Status

Observed conditions:
- `PodReadyToStartContainers`
- `Initialized`
- `Ready`
- `ContainersReady`
- `PodScheduled`

### What `READY 1/1` means

One container is ready out of one expected container.

### What `RESTARTS 0` means

The container has not restarted.

### QoS Class: BestEffort

- No CPU or memory requests or limits were configured.
- BestEffort Pods are more likely to be evicted under resource pressure.

## 15. Kubernetes Service Account Mount

Pods automatically mount:

```text
/var/run/secrets/kubernetes.io/serviceaccount
```

It may contain:
- Service account token
- Cluster CA certificate
- Namespace information

This is used when a Pod needs to communicate with the Kubernetes API.
Production access should be controlled through RBAC and least privilege.
