#!/bin/bash

cd /home/ermoffa/toasters/terraform/jenkins

if [ -x ./terraform_jenkins.sh ]; then

    echo "Executing script to launch jenkins"

else

    echo "Script not found or is not executable"
    exit 1
fi