package cms.sre.terraform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class is for event received from gitlab_webhook Microservice
 * @author ermoffa
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebHookMicroServiceEvent {
	
	private String object_kind;
	
	private String user_email;
	
	private String ssl_url;
	
	private String user_name;
	
	private String pull_number;
	
	private String branch_name;
	
	private String revision_numer;
	
	private String project_name;
	
	private String classification;
	
	private String timestamp;	
	
	public String getObject_kind() {
		return object_kind;
	}

	public void setObject_kind(String object_kind) {
		this.object_kind = object_kind;
	}
	
	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getSsl_url() {
		return ssl_url;
	}

	public void setSsl_url(String ssl_url) {
		this.ssl_url = ssl_url;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPull_number() {
		return pull_number;
	}

	public void setPull_number(String pull_number) {
		this.pull_number = pull_number;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getRevision_numer() {
		return revision_numer;
	}

	public void setRevision_numer(String revision_numer) {
		this.revision_numer = revision_numer;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "WebHookMicroServiceEvent [object_kind=" + object_kind + ", user_email=" + user_email + ", ssl_url="
				+ ssl_url + ", user_name=" + user_name + ", pull_number=" + pull_number + ", branch_name=" + branch_name
				+ ", revision_numer=" + revision_numer + ", project_name=" + project_name + ", classification="
				+ classification + ", timestamp=" + timestamp + "]";
	}
    
}
