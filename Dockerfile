FROM alpine:3.7

RUN apk add --update \
    openjdk8-jre

COPY appli.jar appli.jar
COPY properties.yml properties.yml

ENTRYPOINT ["java", "-jar", "appli.jar", "server", "properties.yml"]
