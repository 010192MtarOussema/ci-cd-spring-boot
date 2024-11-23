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
                bat './mvnw test'

            }
        }

        stage('Build Application') {
            steps {

                echo '...  Building application  ...'
                bat './mvnw clean install'
                echo 'Building application sucées...'
                
             
            }
        }
         stage('Scan') { // Étape d'analyse SonarQube
            steps {
                withSonarQubeEnv('sq1') { // Utilisation de l'environnement SonarQube configuré
                bat './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.java.binaries=target/classes'
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
}