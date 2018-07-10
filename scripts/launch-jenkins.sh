#!/bin/bash

source ~/.bashrc

cd /home/ermoffa/toasters/terraform/jenkins

echo $(env)

echo "Now in directory: " $(pwd)

# Script details for launching jenkins instance

if [ -x terraform-destroy-jenkins.sh ]; then

    echo "Executing script terraform-destroy-jenkins.sh"

else

    echo "Script not found or not executable"
    exit 1

fi