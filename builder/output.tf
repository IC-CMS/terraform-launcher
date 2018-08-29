output "jenkins_url" {
  value = "${var.jenkins_url}"
}

output "git_url" {
  value = "${var.git_url}"
}

output "user" {
  value = "${data.consul_keys.jenkins.var.jenkins_build_username}"

}


