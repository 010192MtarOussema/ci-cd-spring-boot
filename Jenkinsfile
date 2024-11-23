pipeline {
    agent any

    stages {
        stage('Build Application') {
            steps {
                echo 'Building application...'
                bat './mvnw clean package' // Compile et package le JAR
            }
        }

        stage('Deploy to VM') {
            steps {
                echo 'Deploying to VM...'
                sh '''
                    # Définissez les variables
                    VM_USER=jenkinsuser
                    VM_IP=192.168.1.100
                    REMOTE_DIR=/home/jenkinsuser
                    JAR_FILE=target/demo-ci-cd-0.0.1-SNAPSHOT.jar

                    # Copier le fichier JAR
                    scp $JAR_FILE $VM_USER@$VM_IP:$REMOTE_DIR/

                    # Exécuter des commandes sur la VM
                    ssh $VM_USER@$VM_IP << EOF
                        echo "Stopping previous application..."
                        pkill -f demo-ci-cd-0.0.1-SNAPSHOT.jar || echo "No application running"
                        echo "Starting new application..."
                        nohup java -jar $REMOTE_DIR/demo-ci-cd-0.0.1-SNAPSHOT.jar > $REMOTE_DIR/app.log 2>&1 &
                    EOF
                '''
            }
        }
    }

    post {
        success {
            echo 'Deployment succeeded!'
        }
        failure {
            echo 'Deployment failed. Check the logs.'
        }
    }
}
