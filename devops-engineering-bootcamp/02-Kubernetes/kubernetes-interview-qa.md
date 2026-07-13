# Kubernetes Interview Q&A

This file includes Kubernetes Day 1 questions with beginner-friendly answers and architect-level answers.

## 1. Why does Kubernetes exist?

**Beginner answer:** Kubernetes exists to manage containers at scale. It automates deployment, scaling, and recovery so teams do not have to manage every container manually.

**Architect answer:** Kubernetes provides a declarative control plane, reconciliation loops, and distributed scheduling. It solves application availability, service discovery, load balancing, and self-healing across multiple nodes.

## 2. Docker vs Kubernetes?

**Beginner answer:** Docker builds images and runs containers. Kubernetes schedules those containers, manages their lifecycle, and keeps them running.

**Architect answer:** Docker operates at the container runtime layer. Kubernetes sits above that layer to orchestrate containerized workloads, maintain desired state, and manage multi-node deployments.

## 3. Kubernetes vs OpenShift?

**Beginner answer:** OpenShift is an enterprise Kubernetes platform from Red Hat. It includes Kubernetes plus extra security, a UI, integrated registry, and CI/CD features.

**Architect answer:** OpenShift extends Kubernetes with enterprise policies, multi-tenancy, built-in image registry, developer console, and custom operators. It standardizes secure application delivery for regulated environments.

## 4. What is a cluster?

**Beginner answer:** A cluster is a group of machines that run Kubernetes workloads together.

**Architect answer:** A cluster is a distributed system composed of control plane and worker nodes that exposes the Kubernetes API and provides resilient orchestration across physical or virtual infrastructure.

## 5. What is a node?

**Beginner answer:** A node is a machine where Pods run.

**Architect answer:** A node is a Kubernetes worker or control-plane host that runs `kubelet`, `kube-proxy`, a container runtime, and optionally workloads in multi-role environments.

## 6. What is a Pod?

**Beginner answer:** A Pod is the smallest deployable unit in Kubernetes. It can contain one or more containers.

**Architect answer:** A Pod represents one or more containers with shared networking and storage. It is scheduled as a unit and models the atomic execution boundary for Kubernetes workloads.

## 7. Why does Kubernetes use Pods?

**Beginner answer:** Pods group containers that need to work together. They share IP addresses and storage.

**Architect answer:** Pods abstract co-located containers as a unit of deployment. This allows Kubernetes to manage networking, lifecycle, and affinity at the correct granularity while enabling sidecars and init containers.

## 8. Can one Pod contain multiple containers?

**Beginner answer:** Yes, a Pod can have multiple containers that run together.

**Architect answer:** Multiple containers in a Pod share networking and storage. Use this for sidecars, logging agents, or helper containers that support the main application.

## 9. What is a sidecar?

**Beginner answer:** A sidecar is a secondary container that supports the main application container in the same Pod.

**Architect answer:** A sidecar pattern places auxiliary functionality such as logging, proxying, or configuration synchronization alongside the main app container in the same Pod.

## 10. What is a namespace?

**Beginner answer:** A namespace is a logical grouping for resources in a cluster.

**Architect answer:** Namespaces partition cluster resources for organization, access control, and multi-tenant isolation. They are useful for separate environments like development, staging, and production.

## 11. What is kubelet?

**Beginner answer:** kubelet is the agent that runs on each node.

**Architect answer:** kubelet is a node-level service that registers the node, syncs Pod state with the API Server, and manages container lifecycle with the runtime.

## 12. What is kube-proxy?

**Beginner answer:** kube-proxy handles networking for Services.

**Architect answer:** kube-proxy implements Service virtual IPs and forwarding rules through iptables or IPVS so Pods can communicate reliably via stable endpoints.

## 13. What is containerd?

**Beginner answer:** containerd is a container runtime used by Kubernetes.

**Architect answer:** containerd is a lightweight runtime that pulls images, manages container lifecycles, and provides an interface for orchestration systems like Kubernetes.

## 14. What is etcd?

**Beginner answer:** etcd stores Kubernetes cluster data.

**Architect answer:** etcd is a strongly consistent distributed key-value store that persists desired state, configuration, and cluster metadata.

## 15. What is the API Server?

**Beginner answer:** The API Server is the Kubernetes control plane entrypoint.

**Architect answer:** The API Server validates and persists API objects, handles authentication and authorization, and serves as the communication hub for all Kubernetes components.

## 16. What is the scheduler?

**Beginner answer:** The scheduler chooses which node will run a Pod.

**Architect answer:** The scheduler evaluates resource requirements, node selectors, taints, and policies to bind new Pods to appropriate nodes.

## 17. What is the controller manager?

**Beginner answer:** The controller manager runs controllers that keep the cluster in the desired state.

**Architect answer:** The controller manager hosts multiple controllers that watch resources and take corrective actions to reconcile actual state with desired state.

## 18. Desired state vs actual state?

**Beginner answer:** Desired state is what you ask Kubernetes to run. Actual state is what is currently running.

**Architect answer:** Desired state is the declared configuration stored in etcd. Actual state is the live cluster state observed by controllers and reported through the API Server.

## 19. What is reconciliation?

**Beginner answer:** Reconciliation is Kubernetes fixing differences between desired and actual state.

**Architect answer:** A reconciliation loop continuously observes resource state and triggers controllers to create, update, or delete resources until desired state is reached.

## 20. What is self-healing?

**Beginner answer:** Self-healing means Kubernetes replaces failed Pods automatically.

**Architect answer:** Self-healing is the controller-driven process where Kubernetes detects failures and recreates missing or unhealthy resources without manual intervention.

## 21. Standalone Pod vs Deployment-managed Pod?

**Beginner answer:** A standalone Pod is created directly and may not be recreated. A Deployment-managed Pod is managed by a controller and is recreated automatically.

**Architect answer:** Standalone Pods are not part of a higher-level controller. Deployment-managed Pods are controlled by ReplicaSets, which enforce replica counts and update strategies.

## 22. How does Kubernetes detect a crashed container?

**Beginner answer:** The runtime detects the crash, and kubelet reports status to Kubernetes.

**Architect answer:** The container runtime signals exit status, kubelet updates Pod conditions to the API Server, and controllers compare that with desired state to decide if a new Pod is needed.

## 23. How do you investigate a crashed Pod?

**Beginner answer:** Use `kubectl describe pod` and `kubectl logs`.

**Architect answer:** Inspect Pod events, container exit codes, resource usage, previous logs, and node conditions to identify crash causes and remediation steps.

## 24. What does `kubectl logs --previous` do?

**Beginner answer:** It shows the logs from the previously terminated container instance.

**Architect answer:** It retrieves logs from the terminated container before the current restart cycle, which is useful for diagnosing crash loops and transient failures.

## 25. What is CrashLoopBackOff?

**Beginner answer:** CrashLoopBackOff means a container keeps failing soon after startup.

**Architect answer:** CrashLoopBackOff is a restart backoff state where kubelet delays container restarts after repeated failures.

## 26. What is OOMKilled?

**Beginner answer:** OOMKilled means the container was killed because it used too much memory.

**Architect answer:** OOMKilled is a kernel out-of-memory event where the operating system terminated the container process due to memory pressure.

## 27. Why are Pod IPs unstable?

**Beginner answer:** Pod IPs change when Pods are recreated.

**Architect answer:** Pod IPs are ephemeral because Pods are disposable units. Stable networking is provided by Services, not Pod IPs.

## 28. What are labels?

**Beginner answer:** Labels are key/value tags attached to objects.

**Architect answer:** Labels are metadata used for organizing, selecting, and grouping Kubernetes resources across controllers and Services.

## 29. What are selectors?

**Beginner answer:** Selectors find objects by labels.

**Architect answer:** Selectors evaluate label queries to identify sets of resources for management and routing.

## 30. How does a Service identify Pods?

**Beginner answer:** A Service uses label selectors to match Pods.

**Architect answer:** A Service watches Pods that match its selector and updates its endpoints list for load balancing and DNS discovery.

## 31. What is CoreDNS?

**Beginner answer:** CoreDNS is the DNS server in Kubernetes.

**Architect answer:** CoreDNS resolves service names within the cluster and provides DNS-based discovery for Pods and Services.

## 32. What is kind?

**Beginner answer:** kind is Kubernetes in Docker.

**Architect answer:** kind is a tool for running local Kubernetes clusters in Docker containers, often used for development and CI testing.

## 33. What is OpenShift?

**Beginner answer:** OpenShift is Red Hat's enterprise Kubernetes platform.

**Architect answer:** OpenShift combines Kubernetes with enterprise security, developer tools, integrated registry, and platform services for managed workloads.

## 34. What is a rolling update?

**Beginner answer:** A rolling update updates application Pods gradually.

**Architect answer:** A rolling update is a Deployment strategy that replaces Pods incrementally to avoid downtime while updating applications.
