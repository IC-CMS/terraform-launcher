#!/usr/bin/env bash

set -e

if [ "$1" != "" ]; then

    release_name="release/$1"

else

    release_name=$(git branch -r | tail -n1 | sed 's/.*origin\////')

fi

echo "checking out '$release_name' ..."

git checkout $release_name