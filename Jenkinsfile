pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Checking out code...'
                checkout scm
                echo "Current branch: ${env.BRANCH_NAME}"
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running tests...'
            }
        }

        stage('Build Application') {
            steps {
                echo '...  Building application  ...'
                bat './mvnw test'
                echo 'Building application sucées...'
                
             
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploying application to production...'

             
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded for branch: ${env.BRANCH_NAME}"
        }
        failure {
            echo "Pipeline failed for branch: ${env.BRANCH_NAME}. Check logs for details."
        }
    }
}
