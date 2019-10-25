FROM openjdk:8
EXPOSE 8080
ADD target/CreditCardValidator-0.0.1-SNAPSHOT.jar CreditCardValidator-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/CreditCardValidator-0.0.1-SNAPSHOT.jar"]
