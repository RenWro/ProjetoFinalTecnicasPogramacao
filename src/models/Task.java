package models;

import utils.enums.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Task {
    public UUID id;
    public String title;
    public String description;
    public LocalDateTime expirationDate;
    public TaskPriority priority;
    public TaskStatus status;
    public List<String> tags; //TODO lista de utils.enums.TaskTag

    public Task(UUID id, String title, String description, LocalDateTime expirationDate, TaskPriority priority, TaskStatus status, List<String> tags) {
//        id = controllers.TaskController.generateId(); TODO criar gerador de id
        this.id = UUID.randomUUID();
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
