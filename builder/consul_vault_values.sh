vault2 write secret/jenkins/testuser_password value=''
vault2 write secret/jenkins/git_deploy_private_key value=@id_rsa
vault2 write secret/jenkins/token value=''

consul

consul write jenkins/jenkins_build_username=''


