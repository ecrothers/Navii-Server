FROM java:8
COPY navi.jar navi.jar
COPY application.properties application.properties
EXPOSE 80
ENTRYPOINT ["java", "-Xms1g", "-Xmx3g", "-Djava.security.egd=file:/dev/./urandom", "-Denv=@environment@", "-Dversion=@version@", "-jar", "/navi.jar"]