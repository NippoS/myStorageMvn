FROM openjdk:8
ADD target/myStorageMvn.jar myStorageMvn.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "myStorageMvn.jar"]