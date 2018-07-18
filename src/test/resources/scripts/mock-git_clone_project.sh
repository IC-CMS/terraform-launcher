#!/usr/bin/env bash

# This script will clone a git repository to a specific location
# Instructions

function usage() {

    echo "USAGE: "
    echo "-r <git_ssh_url>"
    echo "-l <Local Directory>"
    echo "-p <Project Name>"
    echo "-h display this help message"
    echo "-o <optional args>"

    return 0

}

while getopts ":g:l:m:o:h?" opt ;

do
    case "$opt" in
        g)
            GIT_SSH_URL="${OPTARG}"
            ;;
        l)
            LOCALDIR="${OPTARG}"
            ;;
        n)
            PROJECT="${OPTARG}"
            ;;
        o)
            OPTION="${OPTARG}"
            ;;
        h|?)
            usage
            exit 0
            ;;
    esac
done

shift $((OPTIND-1))

user_and_hostname=$(echo $GIT_SSH_URL | cut -d ":" -f 1)

remoteRepoAccessible=$(ssh $user_and_hostname)

if [[ $remoteRepoAccessible =~ "^Welcome*" ]]; then

    cloneCmd="git clone $GIT_SSH_URL $LOCALDIR"

    cloneCmdRun=$($cloneCmd 2>&1)

    echo -e "Running : \n $cloneCmd"

    echo -e "${cloneCmdRun}\n\n"

else

    echo -e "Unable to connecto to repository\n"
    exit 1

fi