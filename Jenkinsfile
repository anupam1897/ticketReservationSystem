pipeline {
    agent any 

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning repository from GitHub...'
                // Using the shorthand git step
                git branch: 'main', url: 'https://github.com/anupam1897/ticketReservationSystem.git'
            }
        }

        stage('Build & Unit Test') {
            steps {
                echo 'Compiling and Testing with JDK 21 in WSL...'
                // Changed 'bat' to 'sh' for Linux compatibility
                sh 'mvn compile'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
        success {
            echo 'Pipeline finished successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the console output above.'
        }
    }
}