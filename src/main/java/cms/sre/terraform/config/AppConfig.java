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
}

