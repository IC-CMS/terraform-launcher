package cms.sre.terraform.model;

import java.util.List;

public class GitLabCommit {
    private String id;
    private String message;
    private String timestamp;
    private String url;
    private GitLabAuthor author;
    private List<String> added;
    private List<String> modified;
    private List<String> removed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public GitLabAuthor getAuthor() {
        return author;
    }

    public void setAuthor(GitLabAuthor author) {
        this.author = author;
    }

    public List<String> getAdded() {
        return added;
    }

    public void setAdded(List<String> added) {
        this.added = added;
    }

    public List<String> getModified() {
        return modified;
    }

    public void setModified(List<String> modified) {
        this.modified = modified;
    }

    public List<String> getRemoved() {
        return removed;
    }

    public void setRemoved(List<String> removed) {
        this.removed = removed;
    }
}
