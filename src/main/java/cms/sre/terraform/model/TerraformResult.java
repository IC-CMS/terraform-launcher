package cms.sre.terraform.model;

public interface TerraformResult {

    public String getAction();

    public void setAction(String action);

    public String getStatus();

    public void setStatus(String status);

    public int getResourcesAdded();

    public void setResourcesAdded(int assets);

    public int getResourcesChanged();

    public void setResourcesChanged(int assets);

    public int getResourcesDestroyed();

    public void setResourcesDestroyed(int assets);

    public String getHost();

    public void setHost(String host);
}