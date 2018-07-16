#!/bin/bash

# probably need to get this from env variable
cd /home/ermoffa/toasters/terraform/jenkins

if [ -x ./terraform_destroy_jenkins.sh ]; then

    echo "Executing script to destroy jenkins"

else

    echo "Script not found or is not executable"
    exit 1
fi