pipeline {
    agent { label 'aws-ready' }

    environment {
        API_REPO_NAME = 'nr-utopia-user'
        AWS_REGION_ID = "${sh(script:'aws configure get region', returnStdout: true).trim()}"
        AWS_ACCOUNT_ID = "${sh(script:'aws sts get-caller-identity --query "Account" --output text', returnStdout: true).trim()}"
        UTOPIA_MICROSERVICE_USERS_PORT=credentials('nr_utopia_users_port')
        UTOPIA_DB_PORT=credentials('nr_utopia_db_port')
        UTOPIA_DB_HOST=credentials('nr_utopia_db_host')
        UTOPIA_DB_NAME=credentials('nr_utopia_db_name')
        UTOPIA_DB_LOGIN=credentials('nr_utopia_db_login')
        UTOPIA_DB_USER="${env.UTOPIA_DB_LOGIN_USR}"
        UTOPIA_DB_PASSWORD="${env.UTOPIA_DB_LOGIN_PSW}"
        UTOPIA_JWT_SECRET=credentials('nr_utopia_jwt_secret')
        SONARQUBE_ID = tool name: 'SonarQubeScanner-4.6.2'
    }

    stages {
        stage('Setup') {
            steps {
                sh 'export AWS_PROFILE=nick'
            }
        }
        stage('AWS') {
            steps {
               echo 'logging in via AWS client'
               sh 'aws ecr get-login-password --region ${AWS_REGION_ID} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION_ID}.amazonaws.com'
            }
        }
        stage('Build') {
            steps {
                sh 'docker context use default'
                sh "mvn clean package"
            }
        }
        stage('SonarQube') {
             steps {
                echo 'Running SonarQube Quality Analysis'
                withSonarQubeEnv('SonarQube') {
                    sh """
                       ${SONARQUBE_ID}/bin/sonar-scanner \
                       -Dsonar.projectKey=nr-utopia-user \
                       -Dsonar.sources=./src/main/java/com/smoothstack/utopia/user \
                       -Dsonar.java.binaries=./target/classes/com/smoothstack/utopia/user
                    """
                }
                timeout(time: 15, unit: 'MINUTES') {
                    sleep(10)
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Docker') {
            steps {
                echo 'Building Docker Image'
                sh 'docker build -t ${API_REPO_NAME} .'
            }
        }
        stage('Push Images') {
            steps {
                echo 'Tagging images'
                sh 'docker tag ${API_REPO_NAME}:latest ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION_ID}.amazonaws.com/${API_REPO_NAME}:latest'
                echo 'Pushing images'
                sh 'docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION_ID}.amazonaws.com/${API_REPO_NAME}:latest'
            }
        }
        stage('Cleanup') {
            steps {
                echo 'Removing images'
                sh 'docker rmi ${API_REPO_NAME}:latest'
                sh 'docker rmi ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION_ID}.amazonaws.com/${API_REPO_NAME}:latest'
                sh 'unset AWS_PROFILE'
            }
        }
    }
}
