pipeline {
    agent any

    environment {
        MVNW_PATH = './mvnw' // Définit un chemin global pour maven wrapper
    }

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
                bat "${MVNW_PATH} test"
            }
        }

        stage('Build Application') {
            steps {
                echo 'Building application...'
                bat "${MVNW_PATH} clean install"
                echo 'Building application success...'
            }
        }

        stage('Scan') { // Étape d'analyse SonarQube
            steps {
                withSonarQubeEnv('sq1') { // Utilisation de l'environnement SonarQube configuré
                    bat "${MVNW_PATH} org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.java.binaries=target/classes"
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main' // Exécuter uniquement sur la branche principale
            }
            steps {
                echo 'Deploying application...'
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'VM-Centos', // Nom défini dans Publish Over SSH
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'target/demo-ci-cd-0.0.1-SNAPSHOT.jar', // Fichier JAR spécifique à transférer
                                    remoteDirectory: '/', // Répertoire cible
                                    execCommand: '''
                                        echo "Stopping previous application..."
                                        pkill -f demo-ci-cd-0.0.1-SNAPSHOT.jar || echo "No application running"
                                        echo "Starting new application..."
                                        nohup java -jar target/demo-ci-cd-0.0.1-SNAPSHOT.jar 
                                    '''
                                )
                            ]
                        )
                    ]
                )
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
