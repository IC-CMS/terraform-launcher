# Build project

# in build.tf

provider "jenkins" {
    version = "0.1.0"
    server_url 				= "${var.jenkins_url}"
    username 				= "${data.consul_keys.jenkins.var.jenkins_build_username}"
    password                            = "${data.vault_generic_secret.jenkins_password.data["value"]}"
}

resource "jenkins_job" "jenkins-docker" {
   name = "${var.proj_name}"
   display_name = "jenkins-docker Project"
   description = "jenkins-docker Test Build"
   disabled = false
   parameters = {
	KeepDependencies 		= true,
	GitLabConnection		= "${var.git_url}",
	CredentialsId			= "testuser1",
	TriggerOnPush			= true,
	TriggerOnMergeRequest		= true,
	TriggerOpenMergeRequestOnPush	= "never"
	TriggerOnNoteRequest		= true,
	NoteRegex			= "Jenkins please retry a build",
	CISSkip				= true,
	SkipWorkInProgressMergeRequest	= true,
        BranchFilterType		= "All",
	SecretToken			= "${data.vault_generic_secret.jenkins_token.data["value"]}",
	UserRemoteConfig		= "${var.git_url}",
	BranchSpec			= "${var.git_branch}",
	GenerateSubmoduleConfiguration	= false,
   }

   template = "file://./job_template.xml"

   provisioner "local-exec" {
     command = "curl -u ${data.consul_keys.jenkins.var.jenkins_build_username}:${data.vault_generic_secret.jenkins_password.data["value"]} --location ${var.jenkins_url}/job/${var.proj_name}/build?token=${data.vault_generic_secret.jenkins_token.data["value"]}"	
     }
}
