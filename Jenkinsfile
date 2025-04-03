pipeline {
    agent any

    tools {
        maven 'Maven_Home'  // Ensure Maven_Home is configured in Jenkins
    }

    environment {
        SANITY_TESTS_RAN = 'false'
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
                failure {
                    script {
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }

        stage("Deploy to Dev") {
            when {
                expression { !currentBuild.result || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Deploying to Dev..."
            }
        }

        stage('Run Regression Automation Tests') {
            when {
                expression { !currentBuild.result || currentBuild.result == 'SUCCESS' }
            }
            steps {
                catchError(buildResult: 'SUCCESS') {
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml"
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Regression Extent Report'])
            }
        }

        stage("Deploy to Stage") {
            when {
                expression { !currentBuild.result || currentBuild.result == 'SUCCESS' }
            }
            steps {
                echo "Deploying to Stage..."
            }
        }

        stage('Run Sanity Automation Tests') {
            when {
                expression { !currentBuild.result || currentBuild.result == 'SUCCESS' }
            }
            steps {
                catchError(buildResult: 'SUCCESS') {
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml"
                }
                script {
                    env.SANITY_TESTS_RAN = 'true'
                }
            }
        }

        stage('Publish Sanity Extent Report') {
            when {
                expression { env.SANITY_TESTS_RAN == 'true' }
            }
            steps {
                publishHTML([allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Sanity Extent Report'])
            }
        }
    }
}
