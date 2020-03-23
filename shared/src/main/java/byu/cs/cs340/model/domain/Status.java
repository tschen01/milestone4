package byu.cs.cs340.model.domain;

import java.util.Objects;

public class Status {
    private User user;
    private String content;
    private String timestamp;

    public Status() {}

    public Status(User user, String content, String timestamp) {
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return user.getName();
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAlias() {
        return user.getAlias();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        if ((status.getAlias().equals(this.getAlias()) && status.getContent().equals(this.getContent()) && status.getTimestamp().equals(this.getTimestamp()))) {
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(user, timestamp);
    }

    @Override
    public String toString() {
        return "Status{" +
                "user=" + user.getAlias() +
                ", content=" + content +
                ", timestamp" + timestamp +
                '}';
    }

}
