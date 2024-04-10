# Task Manager

The Task Manager is a simple yet powerful Java application designed to effectively manage and schedule tasks. It offers functionalities like adding, editing, deleting tasks, automatically marking overdue tasks, and writing tasks to a CSV file for persistence. It leverages Java's concurrency features for efficient task processing.

## Features

- **Task Management:** Create, edit, and delete tasks with ease.
- **Automatic Overdue Checking:** Automatically checks and updates tasks as overdue if their expiration date has passed.
- **CSV Export:** Periodically writes tasks to a CSV file for persistence.
- **Concurrent Task Processing:** Utilizes Java's `ScheduledExecutorService` for task scheduling and execution.

## Project Structure

The project adheres to the Model-View-Controller (MVC) architecture, separating the concerns of data (Model), presentation (View), and user interaction (Controller) for improved organization and maintainability.

## Concepts Applied

This project utilizes several modern Java concepts:

- **Streams:** Used for concise and efficient data processing tasks.
- **Optional:** Provides a safe way to handle potentially null values.
- **Functional Programming (Lambda Expressions):** Enables writing cleaner and more declarative code.
- **Asynchronous Processing:** Utilizes techniques like `ScheduledExecutorService` for concurrent task execution and scheduling.

## Running the Project

To execute the project:

1. Clone the repository to your local environment.
2. Ensure you have the Java Development Kit (JDK) installed.
3. Run the `MainMenu.java` file located in the `view` package to launch the application.


