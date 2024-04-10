package models;

import utils.enums.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

public class Task {
    public UUID id;
    public String title;
    public String description;
    public LocalDate expirationDate;
    public TaskPriority priority;
    public TaskStatus status;


    // constructor with default parameter, calls main constructor. used when there's no UUID defined, generates random one
    public Task(String title, String description, LocalDate creationDate, TaskPriority priority, TaskStatus status) {
        this(UUID.randomUUID(), title, description, creationDate, priority, status);
    }

    // main constructor, with required parameters. used to instantiate a Task with an existing UUID (read from csv)
    public Task(UUID id, String title, String description, LocalDate expirationDate, TaskPriority priority, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
        this.priority = priority;
        this.status = status;
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

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MM yyyy");

        return "ID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Expiration Date: " + expirationDate.format(formatter) + "\n" +
                "Priority: " + priority + "\n" +
                "Status: " + status + "\n";
    }
}
