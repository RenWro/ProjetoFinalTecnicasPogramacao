package utils.csv;

import models.Task;

import java.time.format.DateTimeFormatter;

public class TaskToCSVLine {

    public static String taskToCSVLine(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String expirationDateFormatted = task.expirationDate.format(formatter);
        String tags = String.join(";", task.tags);

        return String.join(",",
                String.valueOf(task.id),
                task.title.replace(",", ";"),
                task.description.replace(",", ";"),
                expirationDateFormatted,
                task.priority.toString(),
                task.status.toString(),
                "\"" + tags + "\""
        );

    }

}
