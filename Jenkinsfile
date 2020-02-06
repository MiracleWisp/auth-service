pipeline {
    agent none
    stages {
        stage('Install npm dependencies') {
            agent {
                docker {
                    image 'node:13-alpine'
                }
            }
            steps {
                sh 'npm i'
            }
        }
        stage('Build application') {
            agent {
                docker {
                    image 'node:13-alpine'
                }
            }
            steps {
                sh 'npm run build-prod'
            }
        }
        stage('Build docker image') {
            agent any
            steps {
                sh 'docker image rm miraclewisp/protein-frontend || true'
                sh 'docker build -t miraclewisp/protein-frontend:${BUILD_NUMBER} -t miraclewisp/protein-frontend:latest .'
            }

        }
        stage('Push docker image') {
            agent any
            steps {
                withDockerRegistry([credentialsId: "dockerhub", url: ""]) {
                    sh 'docker push miraclewisp/protein-frontend:${BUILD_NUMBER}'
                    sh 'docker push miraclewisp/protein-frontend:latest'
                }
            }

        }
        stage('Deploy') {
            agent any
            steps {
                sh 'ssh Rinslet docker stop frontend || true'
                sh 'ssh Rinslet docker image rm miraclewisp/protein-frontend || true'
                sh 'ssh Rinslet docker pull miraclewisp/protein-frontend'
                sh 'ssh Rinslet docker run --rm --name frontend -d -p 80:80 miraclewisp/protein-frontend'
            }
        }
    }
}
