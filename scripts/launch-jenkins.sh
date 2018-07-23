#!/bin/bash

# Script details for launching jenkins instance

if [ -x /scripts/terraform_cms_sre_launch_jenkins.sh ]; then

    scriptPath=$(pwd)
    
    scriptLocation="${scriptPath}/scripts/terraform_cms_sre_launch_jenkins.sh"
    
    echo "Executing script ${scriptLocation}"
    bash -c "${scriptLocation} apply -auto-approve -non-interactive"

    #echo "Result: $#"
else

    echo "Script not found or not executable"
    exit 1

fi
