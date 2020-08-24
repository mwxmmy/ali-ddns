FROM openjdk:11.0.8-jre-slim

ADD ddns-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+08","-Xmx80m","-Xms80m","-XX:+UseSerialGC","-Djava.compiler=NONE","-jar","/app.jar"]
