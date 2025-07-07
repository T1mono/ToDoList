package ru.javadaddy.service;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;
import ru.javadaddy.repository.TaskRepository;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(Task task) {
        taskRepository.createTask(task);
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.getTasks();
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.removeTask(id);
    }

    @Override
    public List<Task> findByStatus(Status status) {
        return taskRepository.filterTaskByStatus(status);
    }

    @Override
    public List<Task> sortByStatus() {
        return taskRepository.sortByStatus();
    }

    @Override
    public List<Task> findByPeriodOfExecution() {
        return taskRepository.sortByPeriodOfExecution();
    }
}
