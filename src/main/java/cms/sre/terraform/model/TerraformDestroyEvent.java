package cms.sre.terraform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TerraformDestroyEvent {

    private String object_kind;

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

    public String getObject_kind() {
        return object_kind;
    }

    public void setObject_kind(String object_kind) {
        this.object_kind = object_kind;
    }
}
