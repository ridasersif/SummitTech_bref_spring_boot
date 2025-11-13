pipeline {
   agent any
   tools{
       maven 'Maven-3.9.11'
       jdk 'JDK-17'
   }
   stages{
       stage('Checkout'){
           steps{
               echo 'Checking out source code...'
               checkout scm
           }
       }
         stage('Build'){
              steps{
                echo 'Building the project...'
                sh 'mvn clean package -DskipTests'
              }
         }
         stage('Test'){
               steps{
                   echo 'Running tests...'
                   sh 'mvn test'
               }
         }
   }


}