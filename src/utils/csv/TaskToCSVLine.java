package utils.csv;

import models.Task;

import java.time.format.DateTimeFormatter;

public class TaskToCSVLine {

    public static String taskToCSVLine(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String expirationDateFormatted = task.expirationDate.format(formatter);
        String tags = String.join(";", task.tags); // Usamos ponto e vírgula para separar as tags

        return String.join(",",
                String.valueOf(task.id),
                task.title.replace(",", ";"), // Substituir vírgulas para evitar conflitos no CSV
                task.description.replace(",", ";"),
                expirationDateFormatted,
                task.priority.toString(),
                task.status.toString(),
                "\"" + tags + "\"" // Aspas para lidar com múltiplos valores
        );

    }

}
