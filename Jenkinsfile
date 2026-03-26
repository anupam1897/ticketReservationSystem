pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token')
        IMAGE_NAME  = "ticket-reservation-system"
        DOCKER_USER = "anupam1897"
        DOCKER_HOST = "unix:///var/run/docker.sock"
        RENDER_SERVICE_ID = "srv-d72j3vq4d50c738qmej0"
        DB_HOST = "aws-1-ap-northeast-1.pooler.supabase.com"
        DB_PORT = "6543"
        DB_NAME = "postgres"
        DB_USER = "postgres.ipkraldnreovycucpcyk"
        SERVER_PORT = "8080"
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
                    env.FULL_IMAGE = "${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}"

                    echo "Git Tag   : ${env.GIT_TAG}"
                    echo "Git Hash  : ${env.GIT_HASH}"
                    echo "Image Tag : ${env.IMAGE_TAG}"
                    echo "Full Image: ${env.FULL_IMAGE}"
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
                echo 'All tests passed successfully!'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    echo 'JUnit results published.'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                echo 'Generating JaCoCo report...'
                sh 'mvn jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
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
                echo 'Packaging JAR...'
                sh 'mvn package -DskipTests'
                sh 'ls -l target/*.jar'
            }
        }

        stage('Archive') {
            steps {
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
                    echo "Docker image built successfully"
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

                        sh """
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

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

        stage('Set Render Environment Variables') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'RENDER_API_KEY', variable: 'RENDER_API_KEY'),
                        string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD')
                    ]) {

                        def payload = """
                        {
                        "envVars": [
                            {"key": "DB_HOST", "value": "${env.DB_HOST}"},
                            {"key": "DB_PORT", "value": "${env.DB_PORT}"},
                            {"key": "DB_NAME", "value": "${env.DB_NAME}"},
                            {"key": "DB_USER", "value": "${env.DB_USER}"},
                            {"key": "DB_PASSWORD", "value": "${DB_PASSWORD}"},
                            {"key": "SERVER_PORT", "value": "${env.SERVER_PORT}"}
                        ]
                        }
                        """

                        sh """
                        curl -X PUT https://api.render.com/v1/services/${RENDER_SERVICE_ID}/env-vars \
                        -H "Authorization: Bearer ${RENDER_API_KEY}" \
                        -H "Content-Type: application/json" \
                        -d '${payload}'
                        """
                    }
                }
            }
        }

        stage('Deploy to Render') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'RENDER_API_KEY', variable: 'RENDER_API_KEY'),
                        string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD')
                    ]) {

                        sh """
                        curl -X POST https://api.render.com/v1/services/${RENDER_SERVICE_ID}/deploys \
                          -H "Authorization: Bearer ${RENDER_API_KEY}" \
                          -H "Content-Type: application/json" \
                          -d '{
                            "imageUrl": "${FULL_IMAGE}"
                          }'
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo """
            PIPELINE SUCCESS
            ─────────────────────────────
            Git Tag   : ${GIT_TAG}
            Git Hash  : ${GIT_HASH}
            Image Tag : ${IMAGE_TAG}
            Image     : ${FULL_IMAGE}
            ─────────────────────────────
            """
        }

        failure {
            echo "PIPELINE FAILED — check logs"
        }

        always {
            sh "docker logout || true"
            echo "Cleanup complete"
        }
    }
}