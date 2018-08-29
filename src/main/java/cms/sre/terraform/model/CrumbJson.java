
package cms.sre.terraform.model;

/**
 * This class is for holding the crumb issued by Jenkins
 */
public class CrumbJson {

    private String crumb;
    private String crumbRequestField;

    public String getCrumb() {
        return crumb;
    }

    public void setCrumb(String crumb) {
        this.crumb = crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public void setCrumbRequestField(String crumbRequestField) {
        this.crumbRequestField = crumbRequestField;
    }

    @Override
    public String toString() {
        return "CrumbJson{" +
                "crumb='" + crumb + '\'' +
                ", crumbRequestField='" + crumbRequestField + '\'' +
                '}';
    }
}