pipeline {
    agent any 

    tools {
        // Names must match exactly what you set in Manage Jenkins -> Tools
        jdk 'Java21'
        maven 'Maven3.9'
    }

    environment {
        APP_NAME = "ticket-reservation-system"
        DOCKER_IMAGE = "anupam1897/${APP_NAME}"
        // Credentials ID for your Docker Hub/Registry if pushing
        // DOCKER_CREDS = 'docker-hub-credentials' 
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Cloning repository from GitHub..."
                // Ensure branch matches your repo (main vs master)
                git branch: 'main', url: 'https://github.com/anupam1897/ticketReservationSystem.git'
            }
        }

        stage('Build & Unit Test') {
            steps {
                echo "Compiling and Testing with JDK 21..."
                // 'bat' is required for Windows Command Prompt
                bat 'mvn clean package'
            }
        }

        stage('Dockerize') {
            steps {
                echo "Building Docker Image..."
                // Building the image locally using Windows Docker Desktop
                bat "docker build -t ${DOCKER_IMAGE}:latest ."
            }
        }

        stage('Verify Deployment') {
            steps {
                echo "Verifying Docker Image..."
                bat "docker images | findstr ${APP_NAME}"
            }
        }
    }

    post {
        success {
            echo "Successfully built ${APP_NAME}!"
        }
        failure {
            echo "Pipeline failed. Check the console output above."
        }
        always {
            echo "Cleaning up workspace..."
            cleanWs()
        }
    }
}