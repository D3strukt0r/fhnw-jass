# The different stages of this Dockerfile are meant to be built into separate images
# https://docs.docker.com/develop/develop-images/multistage-build/#stop-at-a-specific-build-stage
# https://docs.docker.com/compose/compose-file/#target

# -------
# Builder
# -------
FROM gradle:jdk8 AS build

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . .

RUN set -eux; \
	\
    gradle lib:build --no-daemon; \
	gradle server:build --no-daemon

# ---------------
# Final Container
# ---------------
FROM openjdk:8-jre-slim

WORKDIR /data

COPY --from=build /home/gradle/src/server/build/libs/jass-server.jar /opt/jass-server.jar

ENTRYPOINT ["java", "-jar", "/opt/jass-server.jar"]
