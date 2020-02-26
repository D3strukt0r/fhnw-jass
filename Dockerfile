# -------
# Builder
# -------

FROM gradle:jdk8 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle lib:build --no-daemon
RUN gradle server:build --no-daemon

# ---------------
# Final Container
# ---------------

FROM openjdk:8

RUN mkdir -p /app/data
COPY --from=build /home/gradle/src/server/build/libs/server-0.0.1.jar /app/jass-server.jar

WORKDIR /app
VOLUME ["/app/data"]
EXPOSE 2000
ENTRYPOINT ["java", "-jar", "jass-server.jar"]
