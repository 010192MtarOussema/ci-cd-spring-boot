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
                echo 'Building application success...'
            }
        }

        stage('Scan') { // Étape d'analyse SonarQube
            steps {
                withSonarQubeEnv('sq1') { // Utilisation de l'environnement SonarQube configuré
                    bat './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.java.binaries=target/classes'
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main' // Exécuter uniquement sur la branche principale
            }
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'VM-Centos', // Nom défini dans Publish Over SSH
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'target/*.jar', // Fichiers JAR générés après le build
                                    remoteDirectory: '/home/jenkinsuser/deployments', // Répertoire cible sur la VM
                                    execCommand: '''
                                        echo "Stopping previous application..."
                                        nohup java -jar /home/jenkinsuser/deployments/demo-ci-cd-0.0.1-SNAPSHOT.jar > /home/jenkinsuser/deployments/app.log 2>&1 &
                                        echo "Starting new application..."
                                    '''
                                )
                            ]
                        )
                    ]
                    
                )
                echo 'Application deployed successfully to production!'
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
