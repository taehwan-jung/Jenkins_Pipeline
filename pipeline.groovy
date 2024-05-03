pipeline{
    agent any
    environment{
        SONARQUBE_SERVER = 'http://localhost:9000'
        SONAR_TOKEN = 'sqa_5be946c72d531e0c80697b3a3d8aee50d25bcd80'
    }
    stages{
        stage('Build'){
            steps{
                echo "We build the code using build automations tools like maven and npm."
            }
        }
        stage('Unit and integration tests'){
            steps{
                echo "We run unit tests and integration tests using test automation tools like 'Selenium', 'Cypress' and 'appium'." 
            }
            post{
                success{
                    emailext(
                    to: "tehoan94@gmail.com"
                    subject: "test status email"
                    body: "test was successful!"
                    attachmentsPattern: '**/Unite and integration tests/*.log'
                    )
                }
            }
        }
         stage('Code Analysis'){
            steps{
                echo "Integrated sonarqube to analyse the code."
            }
        }
         stage('Security Scan'){
            steps{
                echo "perform security scanning using sonarqube scanner"
                 sh "sonar-scanner -Dsonar.projectKey=your-project-key -Dsonar.sources=. -Dsonar.host.url=${SONARQUBE_SERVER} -Dsonar.login=${SONAR_TOKEN}"
            }
            post{
                success{
                    emailext(
                    to: "tehoan94@gmail.com"
                    subject: "security scanning status email"
                    body: "scanning was successful!"
                    attachmentsPattern: '**/Security Scan/*.log'
                    )
                }
            }
        }
         stage('Deploy to Staging'){
            steps{
                echo "Deploy the code to AWS EC2(staging server)."
            }
        }
         stage('Integration tests on Staging'){
            steps{
                echo "Run the integration test on AWS EC2(staging server) to check if it is working in prodiction-like environment."
            }
        }
         stage('Deploy to Production'){
            steps{
                echo "Deploy the code to the AWS EC2(production server)."
            }
        }
    }
    
}
