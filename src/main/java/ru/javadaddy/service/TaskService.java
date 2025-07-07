package ru.javadaddy.service;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;

import java.util.List;

public interface TaskService {

    public void createTask(Task task);

    List<Task> getAllTask();

    void deleteTask(Long id);

    List<Task> findByStatus(Status status);

    List<Task> sortByStatus();

    List<Task> findByPeriodOfExecution();
}
