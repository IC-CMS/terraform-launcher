#!/bin/bash

source ~/.bashrc

# probably need to get this from env variable
cd /home/ermoffa/toasters/terraform/jenkins

echo $(env)

echo "Now in directory: " $(pwd)

# Script details for launching jenkins instance

if [ -x terraform-destroy-jenkins.sh ]; then

    echo "Executing script terraform-launch-jenkins.sh"

else

    echo "Script not found or not executable"
    exit 1

fi