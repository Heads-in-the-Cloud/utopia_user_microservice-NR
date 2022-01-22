pipeline {
    agent any

    environment {
        UTOPIA_MICROSERVICE_USERS_PORT=credentials('users_port')
        UTOPIA_DB_PORT=credentials('db_port')
        UTOPIA_DB_HOST=credentials('db_host')
        UTOPIA_DB_NAME=credentials('db_name')
        UTOPIA_DB_USER=credentials('db_user')
        UTOPIA_DB_PASSWORD=credentials('db_password')
        UTOPIA_JWT_SECRET=credentials('jwt_secret')
        AWS_ACCOUNT_ID=credentials('aws_account_id')
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }
        stage('Docker') {
            steps {
                sh 'docker build -t user .'
            }
        }
        stage('Push to ECR') {
            steps {
                sh 'aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.us-west-2.amazonaws.com'
                sh 'docker tag user:latest $AWS_ACCOUNT_ID.dkr.ecr.us-west-2.amazonaws.com/user:latest'
                sh 'docker push $AWS_ACCOUNT_ID.dkr.ecr.us-west-2.amazonaws.com/user:latest'
            }
        }
    }
}
