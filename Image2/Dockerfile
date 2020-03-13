FROM openjdk

VOLUME /tmp
ADD continuous-integration-0.0.1-SNAPSHOT.jar myapp2.jar
RUN sh -c 'touch /myapp2.jar'
ENTRYPOINT ["java","-Dspring.profiles.active=loc","-jar","/myapp2.jar"]