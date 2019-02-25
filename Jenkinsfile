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
        sh 'mvn jacoco:prepare-agent test jacoco:report'
      }    
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
        success {
          jacoco(execPattern: '**/target/jacoco.exec', changeBuildStatus: true)        
        }
      }
    }
  
  }
  

}
