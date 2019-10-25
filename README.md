# Getting Started

### github repo - clone
https://github.com/udithnalaka/CCValidationService.git

### build, test, package app
mvn clean install

### run the app
java -jar /target/CreditCardValidator-0.0.1-SNAPSHOT.jar

### swagger ui
http://localhost:8080/swagger-ui.html#/credit-card-controller

#### API endpoints
PUT   /api/v1/cc/validate    validateCreditCard

### dockerizing the app
docker image is created and added to docker hub
* to pull -> docker pull udithnalaka/cc-validator-service
* to run -> docker run -p 9090:8080 udithnalaka/cc-validator-service
* swagger doc -> localhost:9090/swagger-ui.html#/credit-card-controller