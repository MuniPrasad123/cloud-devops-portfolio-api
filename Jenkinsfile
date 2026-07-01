pipeline {
    agent any

    environment {
        APP_NAME = 'cloud-devops-portfolio-api'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        DOCKER_REGISTRY = 'docker.io/your-dockerhub-username'
        DOCKER_IMAGE = "${DOCKER_REGISTRY}/${APP_NAME}:${IMAGE_TAG}"
        DOCKER_IMAGE_LATEST = "${DOCKER_REGISTRY}/${APP_NAME}:latest"
        KUBECONFIG_CREDENTIALS_ID = 'kubeconfig'
        DOCKER_CREDENTIALS_ID = 'docker-registry-credentials'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    docker build -t ${DOCKER_IMAGE} -t ${DOCKER_IMAGE_LATEST} .
                '''
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh '''
                        echo "${DOCKER_PASSWORD}" | docker login "${DOCKER_REGISTRY}" -u "${DOCKER_USERNAME}" --password-stdin
                        docker push ${DOCKER_IMAGE}
                        docker push ${DOCKER_IMAGE_LATEST}
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([file(credentialsId: "${KUBECONFIG_CREDENTIALS_ID}", variable: 'KUBECONFIG')]) {
                    sh '''
                        kubectl apply -f k8s/
                        kubectl set image deployment/${APP_NAME} ${APP_NAME}=${DOCKER_IMAGE}
                        kubectl rollout status deployment/${APP_NAME}
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
