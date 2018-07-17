#!/bin/bash

# probably need to get this from env variable
cd /home/ermoffa/IdeaProjects/terraform-launcher/scripts 

if [ -x ./terraform_aws_sre_destroy_jenkins.sh ]; then

    echo "Executing script to destroy jenkins"
    bash -c "./terraform_aws_sre_destroy_jenkins.sh -force -non-interactive"

    echo "Result: $#"
else

    echo "Script not found or is not executable"
    exit 1
fi
