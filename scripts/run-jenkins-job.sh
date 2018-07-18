#!/bin/bash

source ~/.bashrc

echo $(env)

echo "Now in directory: " $(pwd)

# Script details for running a jenkins job 

if [ -x $(pwd)/run-jenkins-job.sh ]; then

    echo "Executing script run-jenkins-job.sh"

else

    echo "Script not found or not executable"
    exit 1

fi
