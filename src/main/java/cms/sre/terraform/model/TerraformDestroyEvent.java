package cms.sre.terraform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TerraformDestroyEvent {

    private String eventType;

    private String server;

    private String project;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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
                "eventType='" + eventType + '\'' +
                ", server='" + server + '\'' +
                ", project='" + project + '\'' +
                '}';
    }


}
