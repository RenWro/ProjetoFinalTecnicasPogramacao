package controllers;

import models.Task;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TaskController {

    private List<Task> taskList;
    private Set<String> taskTags;
    private final ScheduledExecutorService scheduler;

    public TaskController() {
        this.taskList = new ArrayList<>();
        this.taskTags = new HashSet<>();
        this.scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(this::checkForExpiredTasks, 0, 15, TimeUnit.SECONDS);
    }

    public void addNewTask(String title, String description, LocalDateTime date, TaskPriority priority, TaskStatus status, String... tagNames){
        Set<String> tags = Arrays.stream(tagNames)
                .map(String::toUpperCase)
                .filter(tagName -> !taskTags.contains(tagName))
                .peek(taskTags::add)
                .collect(Collectors.toCollection(HashSet::new));

        Task task = new Task(title, description, date, priority, status, tags);
        taskList.add(task);
    }

    public void deleteTask(Task task){
        taskList.removeIf(t -> t.getId().equals(task.getId()));
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
        task.setStatus(task.getStatus() == TaskStatus.DONE ? TaskStatus.PENDING : TaskStatus.DONE);
    }

    public void checkForExpiredTasks() {
        taskList.stream()
                .filter(task -> task.getExpirationDate().isBefore(LocalDateTime.now()))
                .forEach(task -> task.setStatus(TaskStatus.OVERDUE));
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public Set<String> getTaskTags() {
        return taskTags;
    }
}
