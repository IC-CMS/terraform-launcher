#!/bin/bash

source ~/.bashrc

echo $(env)

echo "Now in directory: " $(pwd)

# Script details for launching jenkins instance

if [ -x $(pwd)/src/test/resources/scripts/mock-terraform_aws_sre_launch_jenkins.sh ]; then

    echo "Executing script mock-aunch-jenkins.sh"

    # emulating a terraform run
    echo "Apply Complete! Resources: 4 added, 0 changed, 0 destroyed."
    echo "private_ip_address = 10.113.15.28"

    echo "Result: $#"
else

    echo "Script not found or not executable"
    exit 1

fi
