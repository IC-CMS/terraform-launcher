package cms.sre.terraform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsJobEvent {

    private String event_type;

    private String jenkins_server;

    private String job_name;

    private String git_repository;

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getGit_repository() {
        return git_repository;
    }

    public void setGit_repository(String git_repository) {
        this.git_repository = git_repository;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getJenkins_server() {
        return jenkins_server;
    }

    public void setJenkins_server(String jenkins_server) {
        this.jenkins_server = jenkins_server;
    }

    @Override
    public String toString() {
        return "JenkinsJobEvent{" +
                "event_type='" + event_type + '\'' +
                ", jenkins_server='" + jenkins_server + '\'' +
                ", job_name='" + job_name + '\'' +
                ", git_repository='" + git_repository + '\'' +
                '}';
    }
}
