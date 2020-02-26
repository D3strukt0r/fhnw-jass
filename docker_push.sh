#!/bin/bash

# Login to make sure we have access to private dockers
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

# Build
docker build -t jass .

# Upload
if [ "$TRAVIS_BRANCH" == "master" ]; then
    DOCKER_PUSH_TAG="latest"
elif [ "$TRAVIS_BRANCH" != "develop" ]; then
    DOCKER_PUSH_TAG="nightly"
elif [ "$TRAVIS_TAG" != "" ]; then
    DOCKER_PUSH_TAG=$TRAVIS_TAG
else
    echo "Skipping deployment because it's neither master, develop or a versioned build"
    exit 0;
fi

docker tag jass "$DOCKER_USERNAME"/fhnw-jass:"$DOCKER_PUSH_TAG"
docker push "$DOCKER_USERNAME"/fhnw-jass:"$DOCKER_PUSH_TAG"
