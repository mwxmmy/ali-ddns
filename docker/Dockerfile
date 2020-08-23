FROM openjdk:11.0.8-jre-slim

ADD ddns-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Xmx50m","-Xms50m","-Xss512k","-XX:+UseSerialGC","-XX:ParallelGCThreads=1","-XX:MaxRAM=72m","-Djava.compiler=NONE","-jar","/app.jar"]
