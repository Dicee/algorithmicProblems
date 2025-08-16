#!/bin/bash

set -x
set -e

account_id='123456789'
ecr_base_url=$account_id.dkr.ecr.us-west-2.amazonaws.com
ecr_repo=$ecr_base_url/crtno/redundancy_detector

docker build -t redundancy_detector . --no-cache
docker tag redundancy_detector:latest $ecr_repo:latest

aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin $ecr_base_url
docker push $ecr_repo:latest

