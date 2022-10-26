#! /bin/bash

cd infra-ts
AWS_PROFILE=CHANGE_ME ENV=dev cdk destroy
