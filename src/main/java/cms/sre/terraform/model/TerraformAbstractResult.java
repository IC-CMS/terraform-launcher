package cms.sre.terraform.model;

public abstract class TerraformAbstractResult implements TerraformResult {

    private String action;
    private String status;
    private int resourcesAdded;
    private int resourcesChanged;
    private int resourcesDestroyed;
    private String host;

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public void setAction(String action) {
        this.action = action;

    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getResourcesAdded() {
        return resourcesAdded;
    }

    @Override
    public void setResourcesAdded(int assets) {
        this.resourcesAdded = assets;
    }

    @Override
    public int getResourcesChanged() {
        return resourcesChanged;
    }

    @Override
    public void setResourcesChanged(int assets) {
        this.resourcesChanged = assets;
    }

    @Override
    public int getResourcesDestroyed() {
        return resourcesDestroyed;
    }

    @Override
    public void setResourcesDestroyed(int assets) {
        this.resourcesDestroyed = assets;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "TerraformAbstractResult{" +
                "action='" + action + '\'' +
                ", status='" + status + '\'' +
                ", resourcesAdded=" + resourcesAdded +
                ", resourcesChanged=" + resourcesChanged +
                ", resourcesDestroyed=" + resourcesDestroyed +
                ", host='" + host + '\'' +
                '}';
    }
}
