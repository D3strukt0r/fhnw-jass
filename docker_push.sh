#!/bin/bash

# Build
docker build -t jass .

# Upload
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

if [ "$TRAVIS_BRANCH" == "master" ]
then
      DOCKER_PUSH_TAG="latest"
else
      DOCKER_PUSH_TAG="nightly"
fi

docker tag back "$DOCKER_USERNAME"/fhnw-jass:"$DOCKER_PUSH_TAG"
docker push "$DOCKER_USERNAME"/fhnw-jass:"$DOCKER_PUSH_TAG"
