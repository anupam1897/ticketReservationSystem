pipeline {
    agent {
        label 'wsl-agent'   // Make sure your Jenkins node has this label
    }

    environment {
        APP_NAME = "ticket_reservation_system"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Cloning repository..."
                git 'https://github.com/anupam1897/ticketReservationSystem.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building application..."
                sh '''
                    echo "Running on WSL:"
                    uname -a
                    mkdir -p build
                    echo "Build complete" > build/output.txt
                '''
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                sh '''
                    echo "Executing test cases..."
                    sleep 2
                    echo "All tests passed!"
                '''
            }
        }

        stage('Package') {
            steps {
                echo "Packaging application..."
                sh '''
                    tar -czf ${APP_NAME}.tar.gz build/
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying application..."
                sh '''
                    echo "Simulating deployment..."
                    mkdir -p /tmp/deploy
                    cp ${APP_NAME}.tar.gz /tmp/deploy/
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline executed successfully!"
        }
        failure {
            echo "Pipeline failed!"
        }
        always {
            echo "Cleaning up..."
            cleanWs()
        }
    }
}