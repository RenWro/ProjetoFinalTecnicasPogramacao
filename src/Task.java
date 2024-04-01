import java.time.LocalDateTime;
import java.util.List;

public class Task {
    public int id;
    public String title;
    public String description;
    public LocalDateTime expirationDate;
    public TaskPriority priority;
    public TaskStatus status;
    public List<String> tags; //TODO lista de TaskTag

    public Task(int id, String title, String description, LocalDateTime expirationDate, TaskPriority priority, TaskStatus status, List<String> tags) {
//        id = TaskController.generateId(); TODO criar gerador de id
        this.id = id;
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
        this.priority = priority;
        this.status = status;
        this.tags = tags;
    }


    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
