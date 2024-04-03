package controllers;

import models.Task;
import utils.enums.TaskPriority;
import utils.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.*;

public class TaskController {

    public List<Task> taskList = new ArrayList<>();
    private Timer timer;

    public TaskController() {
        this.taskList = new ArrayList<>();
        this.timer = new Timer();

        // method checkForExpiredTasks will be executed each minute
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkForExpiredTasks();
            }
        }, 0, 60000); // 60000 milliseconds = 1 minute
    }

    public void addNewTask(String title, String description, LocalDateTime date, TaskPriority priority, TaskStatus status, List<String> tags){
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
        for (Task task : taskList) {
            if (task.getExpirationDate().isBefore(LocalDateTime.now())) {
                task.setStatus(TaskStatus.LATE);
            }
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
