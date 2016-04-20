FROM java:8
COPY navi.jar navi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xms256m", "-Xmx800m", "-Djava.security.egd=file:/dev/./urandom", "-Denv=@environment@", "-Dversion=@version@", "-jar", "/navi.jar"]