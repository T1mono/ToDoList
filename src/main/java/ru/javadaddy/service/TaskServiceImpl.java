package ru.javadaddy.service;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;
import ru.javadaddy.repository.TaskRepository;
import ru.javadaddy.repository.TaskRepositoryImpl;

import java.time.LocalDate;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository = new TaskRepositoryImpl();



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

    @Override
    public Task updateTask(Long id) {

        return null;
    }


}
