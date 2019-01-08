############################################
# Data sources
############################################

# Jenkins data
data "consul_keys" "jenkins" {
  datacenter    = "dc1"

  key {
        name    = "jenkins_build_username"
        path    = "${var.project}/jenkins_build_username"
  }
}

data "vault_generic_secret" "jenkins_password" {
   path = "secret/${var.project}/password"
}

data "vault_generic_secret" "jenkins_token" {
   path = "secret/${var.project}/token"
}

data "vault_generic_secret" "jenkins_git_deploy_private_key" {
   path = "secret/${var.project}/git_deploy_private_key"
}