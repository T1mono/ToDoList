package ru.javadaddy.repository;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository {
    //Создание задачи
    void createTask(Task task);

    //Получить список задач
    List<Task> getTasks();

    //Удалить задачу
    void removeTask(Long id);

    //Фильтровать задачи по статусу
    List<Task> filterTaskByStatus(Status status);

    List<Task> sortByStatus();

    List<Task> sortByPeriodOfExecution();

    Task updateTask(Long id,
                              String newName,
                              String newDescription,
                              LocalDate newDate,
                              Status newStatus
    );
}
