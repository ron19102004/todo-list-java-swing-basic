package org.example.entities;

public class Task {
    private long id;
    private String content;
    private boolean status;
    private User user;
    public Task(){}
    public Task(long id, String content, boolean status, User user) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
