pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token')
        IMAGE_NAME    = "ticket-reservation-system"
        DOCKER_USER   = "anupam1897"
        // DOCKER_HOST   = "tcp://localhost:2375" // Use this if Docker is running in a separate container and exposing the API over TCP
        DOCKER_HOST = "unix:///var/run/docker.sock"
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Cloning repository from GitHub...'
                git branch: 'main', url: 'https://github.com/anupam1897/ticketReservationSystem.git'
                echo 'Repository cloned successfully!'
            }
        }

        stage('Get Version') {
            steps {
                script {
                    env.GIT_TAG = sh(
                        script: "git describe --tags --abbrev=0 2>/dev/null || echo 'v1.0.0'",
                        returnStdout: true
                    ).trim()

                    env.GIT_HASH = sh(
                        script: "git rev-parse --short=7 HEAD",
                        returnStdout: true
                    ).trim()

                    env.IMAGE_TAG = "${env.GIT_TAG}-${env.GIT_HASH}"

                    echo "Git Tag   : ${env.GIT_TAG}"
                    echo "Git Hash  : ${env.GIT_HASH}"
                    echo "Image Tag : ${env.IMAGE_TAG}"
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling source code...'
                sh 'mvn clean compile'
                echo 'Build successful!'
            }
        }

        stage('Test') {
            steps {
                echo 'Running JUnit tests...'
                sh 'mvn test'
                echo 'All test passed successfully!'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    echo 'JUnit test results published to Jenkins.'
                    echo 'Test execution completed!'
                    echo 'Check the test results in Jenkins for details.'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                echo 'Generating JaCoCo coverage report...'
                sh 'mvn jacoco:report'
                echo 'Code coverage report generated successfully!'
                echo 'Coverage report available at: target/site/jacoco/index.html'

            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube code analysis...'
                sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=ticket-reservation-system \
                        -Dsonar.projectName="Ticket Reservation System" \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.token=$SONAR_TOKEN
                '''
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application into JAR...'
                sh 'mvn package -DskipTests'
                echo 'Packaging completed successfully!'
                echo 'Generated JAR file:'
                sh 'ls -l target/*.jar'
                echo 'JAR file is ready for deployment!'
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving JAR artifact...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    sh """
                        docker build \
                          -t ${IMAGE_NAME}:latest \
                          -t ${IMAGE_NAME}:${GIT_TAG} \
                          -t ${IMAGE_NAME}:${IMAGE_TAG} \
                          .
                    """
                    echo "Docker image built with tags:"
                    echo "  → ${IMAGE_NAME}:latest"
                    echo "  → ${IMAGE_NAME}:${GIT_TAG}"
                    echo "  → ${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        // sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}" // This is less secure because the password may appear in logs or process lists
                        // sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin" // This is more secure because it avoids exposing the password in logs or process lists
                        sh """
                            echo '$DOCKER_PASS' | docker login -u $DOCKER_USER --password-stdin
                            docker tag ${IMAGE_NAME}:latest        ${DOCKER_USER}/${IMAGE_NAME}:latest  
                            docker tag ${IMAGE_NAME}:${GIT_TAG}    ${DOCKER_USER}/${IMAGE_NAME}:${GIT_TAG} 
                            docker tag ${IMAGE_NAME}:${IMAGE_TAG}  ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG} 

                            docker push ${DOCKER_USER}/${IMAGE_NAME}:latest   
                            docker push ${DOCKER_USER}/${IMAGE_NAME}:${GIT_TAG}   
                            docker push ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG} 
                        """
                    }
                }
            }
        }

        // stage('Deploy') {
        //     steps {
        //         script {
        //             sh """
        //                 docker stop ticket-reservation || true
        //                 docker rm   ticket-reservation || true

        //                 docker run -d \
        //                   --name ticket-reservation \
        //                   --restart always \
        //                   ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}
        //             """
        //             echo "Deployed: ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
        //         }
        //     }
        // }
    }

    post {
        success {
            echo """
            ✅ Pipeline SUCCESS
            ─────────────────────────────
            Git Tag   : ${GIT_TAG}
            Git Hash  : ${GIT_HASH}
            Image Tag : ${IMAGE_TAG}
            Image     : ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}
            ─────────────────────────────
            """
        }
        failure {
            echo "Pipeline FAILED — check logs above"
        }
        always {
            sh "docker logout"
            echo "Workspace cleaned up"
        }
    }
}