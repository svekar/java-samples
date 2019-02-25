pipeline {
  agent any
  
  tools { 
    maven 'Maven 3.6.0' 
    // jdk 'jdk8' 
  }
  
  stages {
  
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    
    stage('Test') {
      steps {
        sh 'mvn test'
      }    
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }
  
  }
  

}
