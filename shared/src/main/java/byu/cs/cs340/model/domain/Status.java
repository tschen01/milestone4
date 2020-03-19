package byu.cs.cs340.model.domain;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

public class Status {
    private final User user;
    private final String content;
    private final Timestamp timestamp;

    public Status(@NotNull User user, @NotNull String content, @NotNull Timestamp timestamp) {
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return user.getName();
    }

    public String getAlias() {
        return user.getAlias();
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
