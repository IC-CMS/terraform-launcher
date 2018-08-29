# terraform-launcher

# Assumptions

1. Docker and Docker Compose installed on instance
2. terraform plugins installed in /.terraform.d/plugins/linux_amd64
3. Plugins compiled to run in a alpine linux docker container
4. Vault and Consul configured and available for use

vault write secret/jenkins/jenkins_build_user_password value=''
vault write secret/jenkins/git_deploy_private_key value=@id_rsa
vault write secret/jenkins/token value=''

# Aliases define for running terraform in a docker container and running consul and vault commands

alias terraform='sudo docker run --network=host -it --env TF_LOG=DEBUG -v /var/run/docker.sock:/var/run/docker.sock -v $(pwd):/app -v /.terraform.d/plugins/linux_amd64/:/plugins/ -w /app --log-driver=none hashicorp/terraform:0.11.7'

# Location of the terraform builder directory
TF_JOB_BUILD_LOCATION=/home/<install_dir>/terraform-launcher/builder

# Location of the jenkins toaster scripts
TOASTERS_DIR=/home/<install_dir>/toasters
