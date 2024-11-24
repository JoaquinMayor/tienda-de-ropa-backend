FROM amazoncorretto:17-alpine-jdk

COPY target/tienda_ropa-0.0.1-SHAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]