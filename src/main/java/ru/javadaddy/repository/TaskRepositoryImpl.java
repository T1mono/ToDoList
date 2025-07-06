package ru.javadaddy.repository;

import lombok.extern.slf4j.Slf4j;
import ru.javadaddy.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskRepositoryImpl {
    List<Task> tasksRepository = new ArrayList<>();

    public void createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task не может быть равен null");
        }


        if (task.getNameTask() == null || task.getNameTask().isBlank()) {
            throw new IllegalArgumentException("Имя задачи не можеть пустым");
        }

        if (task.getPeriodOfExecution() != null && task.getPeriodOfExecution().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Срок выполнения задачи не может быть в прошлом")
        }

        tasksRepository.add(task);
    }
}
