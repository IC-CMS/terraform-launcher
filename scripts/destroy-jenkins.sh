#!/bin/bash

if [ -x /scripts/terraform_aws_sre_destroy_jenkins.sh ]; then

    echo "Executing script to destroy jenkins"
    bash -c "/scripts/terraform_aws_sre_destroy_jenkins.sh -force -non-interactive"

    echo "Result: $#"
else

    echo "Script not found or is not executable"
    exit 1
fi
