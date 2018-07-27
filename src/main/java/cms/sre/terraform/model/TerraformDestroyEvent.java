package cms.sre.terraform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TerraformDestroyEvent {

    private String type;

    private String server;

    private String project;

    public String getType() {
        return type;
    }

    public void setEvent_type(String eventType) {
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "TerraformDestroyEvent{" +
                "type='" + type + '\'' +
                ", server='" + server + '\'' +
                ", project='" + project + '\'' +
                '}';
    }


}
