#!/bin/bash

# Login to make sure we have access to private dockers
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

# Build
docker build -t jass .

# Upload
if [ "$TRAVIS_BRANCH" == "master" ]
then
      DOCKER_PUSH_TAG="latest"
else
      DOCKER_PUSH_TAG="nightly"
fi

docker tag jass "$DOCKER_USERNAME"/fhnw-jass:"$DOCKER_PUSH_TAG"
docker push "$DOCKER_USERNAME"/fhnw-jass:"$DOCKER_PUSH_TAG"
