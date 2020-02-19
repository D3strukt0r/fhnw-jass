FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle server:build --no-daemon

FROM openjdk:11
EXPOSE 1024
VOLUME ["/app/data"]
RUN mkdir -p /app
COPY --from=build /home/gradle/src/server/build/libs/jass-server.jar /app/jass-server.jar
ENTRYPOINT ["java", "-jar", "/app/jass-server.jar"]
CMD ["-s", "-v"]
