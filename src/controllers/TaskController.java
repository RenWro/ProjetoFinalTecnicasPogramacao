package controllers;

import models.Task;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskController {

    private List<Task> taskList;
    private List<String> taskTags;

    public TaskController() {
        this.taskList = new ArrayList<>();
        this.taskTags = new ArrayList<>();

        // method checkForExpiredTasks will be executed each 30s
        CompletableFuture.runAsync(() -> {
            while (true) {
                checkForExpiredTasks();
                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, Executors.newSingleThreadExecutor()); //TODO otimizar
    }

    public void addNewTask(String title, String description, LocalDateTime date, TaskPriority priority, TaskStatus status, String... tagNames){
        List<String> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            if (!taskTags.contains(tagName)) {
                taskTags.add(tagName.toUpperCase());
            }
            tags.add(tagName.toUpperCase());
        }

        Task task = new Task(title, description, date, priority, status, tags);
        taskList.add(task);
    }

    public void deleteTask(Task task){
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId().equals(task.getId())){
                taskList.remove(i);
                break;
            }
        }
    }

    public void editTitle(Task task, String newTitle) {
        task.setTitle(newTitle);
    }

    public void editDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
    }

    public void editDate(Task task, LocalDateTime newDate) {
        task.setExpirationDate(newDate);
        checkForExpiredTasks();
    }

    public void editPriority(Task task, TaskPriority newPriority) {
        task.setPriority(newPriority);
    }

    public void alterTaskStatus(Task task) {
        if (task.getStatus() == TaskStatus.PENDING) {
            task.setStatus(TaskStatus.DONE);
        } else if (task.getStatus() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.PENDING);
        }
    }

    public void checkForExpiredTasks() {
        taskList.stream()
                .filter(task -> task.getExpirationDate().isBefore(LocalDateTime.now()))
                .forEach(task -> task.setStatus(TaskStatus.OVERDUE));
        System.out.println("Verificação feita");
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<String> getTaskTags() {
        return taskTags;
    }
}
