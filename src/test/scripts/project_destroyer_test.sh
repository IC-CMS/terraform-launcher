#!/usr/bin/env bash

source ~/.bashrc

--env TF_VAR_proj_name=docker-jenkins     --env TF_VAR_git_url=https://github.com/IC-CMS/docker-jenkins.git     --env TF_VAR_git_branch=refs/heads/master     --env TF_VAR_jenkins_url=http://localhost

TERRAFORM="sudo docker run --rm \
    --name jenkins_builder \
    --network=host \
    --env TF_LOG=INFO \
    --env TF_VAR_proj_name=docker-jenkins \
    --env TF_VAR_git_url=https://github.com/ic-cms/docker-jenkins.git  \
    --env TF_VAR_git_branch=refs/heads/master \
    --env TF_VAR_jenkins_url=http://localhost:8089/ \
    --log-driver=none \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v ${TF_JOB_BUILD_LOCATION}:/app \
    -v /.terraform.d/plugins/linux_amd64/:/plugins/ \
    -w /app \
    hashicorp/terraform:0.11.3"

echo "${TERRAFORM}"

#echo "Initializing"
#${TERRAFORM} init -plugin-dir=/plugins

#echo "Creating workspace"
#${TERRAFORM} workspace new jenkinsbuilder

echo "Attempting to destroy jenkins job"
${TERRAFORM} destroy -force
echo "Completed job"
# Probably need to check the statue of the build before actually destroying

#${TERRAFORM} destroy -force

sudo rm -rf $TF_JOB_BUILD_LOCATION/.terraform
sudo rm -rf $TF_JOB_BUILD_LOCATION/terraform*
