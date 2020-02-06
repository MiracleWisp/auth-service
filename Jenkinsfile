pipeline {
    agent none
    stages {
        stage('Clean workspace') {
            agent {
                docker {
                    image 'maven:3.6.3-jdk-8-slim'
                }
            }
            steps {
                sh 'mvn clean'
            }
        }
        stage('Build application') {
            agent {
                docker {
                    image 'maven:3.6.3-jdk-8-slim'
                }
            }
            steps {
                sh 'mvn install'
            }
        }
        stage('Build docker image') {
            agent any
            steps {
                sh 'docker image rm miraclewisp/protein-auth || true'
                sh 'docker build -t miraclewisp/protein-auth:${BUILD_NUMBER} -t miraclewisp/protein-auth:latest .'
            }

        }
        stage('Push docker image') {
            agent any
            steps {
                withDockerRegistry([credentialsId: "dockerhub", url: ""]) {
                    sh 'docker push miraclewisp/protein-auth:${BUILD_NUMBER}'
                    sh 'docker push miraclewisp/protein-auth:latest'
                }
            }

        }
        stage('Deploy') {
            agent any
            steps {
                sh 'ssh Rinslet docker stop auth || true'
                sh 'ssh Rinslet docker image rm miraclewisp/protein-auth || true'
                sh 'ssh Rinslet docker pull miraclewisp/protein-auth'
                sh 'ssh Rinslet docker run --rm --name auth -d -p 80:80 miraclewisp/protein-auth'
            }
        }
    }
}
