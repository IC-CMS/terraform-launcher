#!/bin/bash

source ~/.bashrc

# probably need to get this from env variable
cd /home/ermoffa/IdeaProjects/terraform-launcher/scripts

echo $(env)

echo "Now in directory: " $(pwd)

# Script details for running a jenkins job 

if [ -x run-jenkins-job.sh ]; then

    echo "Executing script run-jenkins-job.sh"

else

    echo "Script not found or not executable"
    exit 1

fi
