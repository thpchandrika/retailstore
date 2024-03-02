pipeline{
    agent any
    tools{
        maven 'maven_3_9_6'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/thpchandrika/retailstore']])
                bat 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                    bat 'docker build -t thpchandrika/springbootmvc-retailstore-img .'
            }
        }
        stage('Push docker image to docker hub'){
            steps{
                withCredentials([string(credentialsId: 'dockerhubpass', variable: 'dockerhub-pwd')]) {
                  bat 'docker login -u thpchandrika -p %dockerhub-pwd%'
                  bat 'docker push thpchandrika/springbootmvc-retailstore-img'
                }
            }
        }
    }
}