package models;

import utils.enums.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Task {
    public UUID id;
    public String title;
    public String description;
    public LocalDateTime expirationDate;
    public TaskPriority priority;
    public TaskStatus status;
    public Set<String> tags;


    // CONSTRUTOR COM PARÂMETRO DEFAULT CHAMA CONSTRUTOR PRINCIPAL, p/
    public Task(String title, String description, LocalDateTime creationDate, TaskPriority priority, TaskStatus status, Set<String> tags) {
        this(UUID.randomUUID(), title, description, creationDate, priority, status, tags);
    }

    // main constructor, with required parameters.
    public Task(UUID id, String title, String description, LocalDateTime expirationDate, TaskPriority priority, TaskStatus status, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
        this.priority = priority;
        this.status = status;
        this.tags = tags;
    }

    // GETTERS AND SETTERS
    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "ExpirationDate: " + expirationDate + "\n" +
                "Priority: " + priority + "\n" +
                "Status: " + status + "\n" +
                "Tags: " + tags + "\n";
    }
}
