#!/bin/bash

export AWS_ACCOUNT=100150877581
export APP_NAME="person-lambda-rest"
export APP_VERSION=$(grep -m 1 '<version>' pom.xml | sed 's/.*<version>\(.*\)<\/version>.*/\1/')

./mvnw clean package

docker build --build-arg APP_VERSION=$APP_VERSION --platform linux/amd64 -t $APP_NAME .

## Onetime create
# aws ecr create-repository --repository-name $APP_NAME --image-scanning-configuration scanOnPush=true --image-tag-mutability MUTABLE

aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin $AWS_ACCOUNT.dkr.ecr.ap-southeast-2.amazonaws.com

docker tag  $APP_NAME:latest $AWS_ACCOUNT.dkr.ecr.ap-southeast-2.amazonaws.com/$APP_NAME:$APP_VERSION
docker push $AWS_ACCOUNT.dkr.ecr.ap-southeast-2.amazonaws.com/$APP_NAME:$APP_VERSION
