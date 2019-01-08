package cms.sre.terraform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="app")
public class AppConfig {

    private String environment;

    private String jenkinsLaunchScript;

    private String jenkinsDestroyScript;

    private String jenkinsJobRunnerScript;

    private String jenkinsHost;

    private String jenkinsPort;

    private String jenkinsUsername;

    private String jenkinsPassword;

    private String emailNotifierUrl;

    private String emailNotifierPort;

    private String emailNotifierPath;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getJenkinsLaunchScript() {
        return jenkinsLaunchScript;
    }

    public void setJenkinsLaunchScript(String jenkinsLaunchScript) {
        this.jenkinsLaunchScript = jenkinsLaunchScript;
    }

    public String getJenkinsDestroyScript() {
        return jenkinsDestroyScript;
    }

    public void setJenkinsDestroyScript(String jenkinsDestroyScript) {
        this.jenkinsDestroyScript = jenkinsDestroyScript;
    }

    public String getJenkinsJobRunnerScript() {
        return jenkinsJobRunnerScript;
    }

    public void setJenkinsJobRunnerScript(String jenkinsJobRunnerScript) {
        this.jenkinsJobRunnerScript = jenkinsJobRunnerScript;
    }

    public String getJenkinsHost() {
        return jenkinsHost;
    }

    public void setJenkinsHost(String jenkinsHost) {
        this.jenkinsHost = jenkinsHost;
    }

    public String getJenkinsPort() {
        return jenkinsPort;
    }

    public void setJenkinsPort(String jenkinsPort) {
        this.jenkinsPort = jenkinsPort;
    }

    public String getJenkinsUsername() {
        return jenkinsUsername;
    }

    public void setJenkinsUsername(String jenkinsUsername) {
        this.jenkinsUsername = jenkinsUsername;
    }

    public String getJenkinsPassword() {
        return jenkinsPassword;
    }

    public void setJenkinsPassword(String jenkinsPassword) {
        this.jenkinsPassword = jenkinsPassword;
    }

    public String getEmailNotifierUrl() {
        return emailNotifierUrl;
    }

    public void setEmailNotifierUrl(String emailNotifierUrl) {
        this.emailNotifierUrl = emailNotifierUrl;
    }

    public String getEmailNotifierPort() {
        return emailNotifierPort;
    }

    public void setEmailNotifierPort(String emailNotifierPort) {
        this.emailNotifierPort = emailNotifierPort;
    }

    public String getEmailNotifierPath() {
        return emailNotifierPath;
    }

    public void setEmailNotifierPath(String emailNotifierPath) {
        this.emailNotifierPath = emailNotifierPath;
    }
}

