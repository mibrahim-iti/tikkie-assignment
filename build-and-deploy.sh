#! /bin/bash

cd tikkie-functions
mvn clean package
cd ../infra-ts
AWS_PROFILE=CHANGE_ME ENV=dev cdk deploy
