FROM adoptopenjdk/openjdk11
WORKDIR /opt
ADD build/libs/typeValidator-0.0.1-SNAPSHOT.jar /opt/app.jar
EXPOSE 8080

RUN sh -c 'touch /opt/app.jar'

ENTRYPOINT ["java", "-Xmx256m",  "-Xms128m","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]