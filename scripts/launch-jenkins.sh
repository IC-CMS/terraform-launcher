#!/bin/bash

source ~/.bashrc

# probably need to get this from env variable
cd /home/ermoffa/IdeaProjects/terraform-launcher/scripts

echo $(env)

echo "Now in directory: " $(pwd)

# Script details for launching jenkins instance

if [ -x terraform_aws_sre_launch_jenkins.sh ]; then

    echo "Executing script terraform-launch-jenkins.sh"
    bash -c "./terraform_aws_sre_launch_jenkins.sh apply -auto-approve -non-interactive"

    echo "Result: $#"
else

    echo "Script not found or not executable"
    exit 1

fi
