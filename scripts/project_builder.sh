#!/bin/bash

set -e

[ -z "$TF_JOB_BUILD_LOCATION" ] && echo "Need to set TF_JOB_BUILD_LOCATION" && exit 1;


echo "BUILD LOCATION: $TF_JOB_BUILD_LOCATION"
function usage() {

    echo "USAGE: "
    echo "-s <jenkins_url>"
    echo "-r <git_ssh_url>"
    echo "-b <git_branch>"
    echo "-p <Project Name>"
    echo "-h display this help message"
    echo "-o <optional args>"

     return 0

}

while getopts "s:r:b:p:o:h?" opt;

do
    case "$opt" in
        s)
            JENKINS_URL="${OPTARG}"
            ;;
        r)
            GIT_SSH_URL="${OPTARG}"
            ;;
        b)
            GIT_BRANCH="${OPTARG}"
            ;;
        p)
            PROJECT="${OPTARG}"
            ;;
        o)
            OPTION="${OPTARG}"
            ;;
        h|?)
            usage
            exit 0
            ;;
    esac
done

shift "$((OPTIND-1))"

# First blow away any existing terraform state

cd $TF_JOB_BUILD_LOCATION

# First blow away any existing terraform state

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
    hashicorp/terraform:0.11.7"

echo "${TERRAFORM}"

echo "Initializing"
bash -c "${TERRAFORM} init -plugin-dir=/plugins"

#echo "Creating workspace"
#bash -c "${TERRAFORM} workspace new jenkinsbuilder"

echo "Attempting to deploy job to jenkins"
bash -c "${TERRAFORM} apply --auto-approve"
echo "Completed job start"