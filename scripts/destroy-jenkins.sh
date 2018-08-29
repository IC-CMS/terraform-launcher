#!/bin/bash

# Script details for launching jenkins instance

# Expected values
# $1 terraform command (e.g. destroy)
# $2 terraform command option (e.g. -force)

echo "Current dir: $(pwd)"
echo "args $1 $2"

if [ -x ${TOASTERS_DIR}/terraform/jenkins/terraform_cms_sre_jenkins.sh ]; then

    echo "Executing script ${TOASTERS_DIR}/terraform/jenkins/terraform_cms_sre_jenkins.sh"
    bash -c "${TOASTERS_DIR}/terraform/jenkins/terraform_cms_sre_jenkins.sh $1 $2"

    echo "Done"
else

    echo "Script not found or not executable"
    exit 1

fi
