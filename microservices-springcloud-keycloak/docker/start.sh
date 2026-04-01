#!/bin/sh

#docker volume prune
docker-compose down &&
docker-compose rm &&
cd .. &&
mvn clean install -DskipTests=true &&
cd docker &&
docker-compose build --no-cache &&
docker-compose up --force-recreate &&

pause