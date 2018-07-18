#!/bin/bash

source ~/.bashrc

# probably need to get this from env variable

echo $(env)

echo "Now in directory: " $(pwd)

# Script details for running a jenkins job 

if [ -x $(pwd)/src/test/resources/scripts/mock-run-jenkins-job.sh ]; then

    echo "Executing script mock-run-jenkins-job.sh"

    # emulating a terraform run
    echo "Apply Complete! Resources: 4 added, 0 changed, 0 destroyed."
    echo "private_ip_address = 10.113.15.28"
else

    echo "Script not found or not executable"
    exit 1

fi
