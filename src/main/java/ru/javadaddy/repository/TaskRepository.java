package ru.javadaddy.repository;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;

import java.util.List;

public interface TaskRepository {
    //Создание задачи
    void createTask(Task task);

    //Получить список задач
    List<Task> getTasks();

    //Удалить задачу
    void removeTask(Long id);

    //Фильтровать задачи по статусу
    //TODO: Передать на стрим, когда будет время и доработать проверки списка задач
    List<Task> filterTaskByStatus(Status status);

    List<Task> sortByStatus();

    List<Task> sortByPeriodOfExecution();
}
