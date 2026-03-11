pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "anupam1897/ticket-reservation"
        DOCKERHUB_CREDENTIALS = "dockerhub-creds"
        SONARQUBE_SERVER = "sonarqube-server"
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            when {
                branch 'dev'
            }
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            when {
                branch 'dev'
            }
            steps {
                withSonarQubeEnv("${SONARQUBE_SERVER}") {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            when {
                branch 'dev'
            }
            steps {
                waitForQualityGate abortPipeline: true
            }
        }

        // stage('Docker Build') {
        //     when {
        //         branch 'main'
        //     }
        //     steps {
        //         sh "docker build -t $DOCKER_IMAGE:${BUILD_NUMBER} ."
        //     }
        // }

        // stage('Push Docker Image') {
        //     when {
        //         branch 'main'
        //     }
        //     steps {
        //         withDockerRegistry(credentialsId: "${DOCKERHUB_CREDENTIALS}", url: '') {
        //             sh "docker push $DOCKER_IMAGE:${BUILD_NUMBER}"
        //         }
        //     }
        // }

        // stage('Terraform Deploy') {
        //     when {
        //         branch 'main'
        //     }
        //     steps {
        //         dir('infrastructure/terraform') {
        //             sh '''
        //             terraform init
        //             terraform apply -auto-approve
        //             '''
        //         }
        //     }
        }

        // stage('Configure Server with Ansible') {
        //     when {
        //         branch 'main'
        //     }
        //     steps {
        //         dir('infrastructure/ansible') {
        //             sh 'ansible-playbook playbook.yml'
        //         }
        //     }
        // }
    }
}