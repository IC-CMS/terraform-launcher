#!/bin/bash

set -e

TF_JOB_BUILD_LOCATION=/home/ermoffa/IdeaProjects/terraform-launcher/builder

[ -z "$TF_JOB_BUILD_LOCATION" ] && echo "Need to set TF_JOB_BUILD_LOCATION" && exit 1;


echo "BUILD LOCATION: $TF_JOB_BUILD_LOCATION"

# First blow away any existing terraform state

cd $TF_JOB_BUILD_LOCATION

TERRAFORM="sudo docker run --rm \
--name jenkins_builder \
--network=host \
--env TF_LOG=INFO \
--env TF_VAR_proj_name=$PROJECT \
--env TF_VAR_git_url=$GIT_SSH_URL \
--env TF_VAR_git_branch=$GIT_BRANCH \
--env TF_VAR_jenkins_url=$JENKINS_URL \
--log-driver=none \
-v /var/run/docker.sock:/var/run/docker.sock \
-v ${TF_JOB_BUILD_LOCATION}:/app \
-v /.terraform.d/plugins/linux_amd64/:/plugins/ \
-w /app \
hashicorp/terraform:0.11.3"

echo "${TERRAFORM}"

echo "Attempting to destroy jenkins job"
${TERRAFORM} destroy -force
echo "Destroyng job"

# Probably need to check the statue of the build before actually destroying

# This probalby isn't needed if the instance is completely blow away each time
sudo rm -rf $TF_JOB_BUILD_LOCATION/.terraform
sudo rm -rf $TF_JOB_BUILD_LOCATION/terraform*