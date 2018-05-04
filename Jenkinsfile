pipeline {
agent {
docker {
image 'maven:3-alpine'
args '-v /root/.m2:/root/.m2'
}
}

stages {
stage('Build') {
steps {
sh 'mvn -f aws/cs4500-spring2018-team-51/pom.xml compile'
sh 'mvn -f aws/cs4500-spring2018-team-51/pom.xml package'
}
}
stage('Test') {
steps {
echo "Testing"
sh 'mvn -f aws/cs4500-spring2018-team-51/pom.xml test'
}
}
stage('SonarQube') {
steps {
withSonarQubeEnv('SonarQube') {
sh 'mvn -f aws/cs4500-spring2018-team-51/pom.xml clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'
sh 'mvn -f aws/cs4500-spring2018-team-51/pom.xml sonar:sonar -Dsonar.host.url=http://ec2-18-220-143-170.us-east-2.compute.amazonaws.com:9000/'
}
}
}
stage('Quality') {
steps {
sh 'sleep 30'
timeout(time: 10, unit: 'SECONDS') {
retry(5) {
script {
def qg = waitForQualityGate()
if (qg.status != 'OK') {
error "Pipeline aborted due to quality gate failure: ${qg.status}"
}
}
}
}
}
}
}
post {
failure {
slackSend (color: '#FF0000', message: "BUILD FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}
success {
slackSend (color: '#00FF00', message: "BUILD SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}
}
}
