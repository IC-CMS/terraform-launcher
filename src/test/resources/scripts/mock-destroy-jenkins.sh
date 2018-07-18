#!/bin/bash

source ~/.bashrc

echo $(env)

if [ -x $(pwd)/src/test/resources/scripts/mock-terraform_aws_sre_destroy_jenkins.sh ]; then

    echo "Executing script to destroy jenkins"
    echo "Destroy complete! Resources: 4 destroyed."
else

    echo "Script not found or is not executable"
    exit 1
fi
