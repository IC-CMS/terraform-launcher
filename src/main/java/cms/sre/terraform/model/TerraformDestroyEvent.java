package cms.sre.terraform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TerraformDestroyEvent {

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    private String server;

    private String project;

    @Override
    public String toString() {
        return "TerraformDestroyEvent{" +
                "server='" + server + '\'' +
                ", project='" + project + '\'' +
                '}';
    }
}
