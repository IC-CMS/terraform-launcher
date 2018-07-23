#!/bin/bash


# Script details for running a jenkins job 

if [ -x /scripts/run-jenkins-job.sh ]; then

    echo "Executing script run-jenkins-job.sh"

else

    echo "Script not found or not executable"
    exit 1

fi
