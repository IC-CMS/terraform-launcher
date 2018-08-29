#!/usr/bin/bash

# Script details for running a jenkins job 

echo "Script home" $(pwd)

echo "HOST $1"
echo "GIT URL: $2"
echo "GIT BRANCH $3"
echo "PROJECT_NAME: $4"

if [ -x scripts/project_builder.sh ]; then

    echo "Executing script project_builder.sh for project $4"

    bash -c "scripts/project_builder.sh -s $1 -r $2 -b $3 -p $4"

    echo "Done"
    exit 0

else

    echo "Script not found or not executable"
    exit 1

fi