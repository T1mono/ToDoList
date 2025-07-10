package ru.javadaddy.repository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {
    List<Task> tasksRepository = new ArrayList<>();

    //Создание задачи
    @Override
    public void createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task не может быть равен null");
        }

        if (task.getNameTask() == null || task.getNameTask().isBlank()) {
            throw new IllegalArgumentException("Имя задачи не может пустым");
        }

        if (task.getPeriodOfExecution() != null && task.getPeriodOfExecution().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Срок выполнения задачи не может быть в прошлом");
        }

        tasksRepository.add(task);
    }

    //Получить список задач
    @Override
    public List<Task> getTasks() {
        if (tasksRepository == null || tasksRepository.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(tasksRepository);
    }

    //Удалить задачу
    @Override
    public void removeTask(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }

        tasksRepository.removeIf(i -> i.getId().equals(id));
    }

    //Фильтровать задачи по статусу
    //TODO: Передать на стрим, когда будет время и доработать проверки списка задач
    @Override
    public List<Task> filterTaskByStatus(Status status) {

        List<Task> result = new ArrayList<>();

        if (status == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }

        for (Task item : tasksRepository) {
            if (item.getStatus().equals(status)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public List<Task> sortByStatus() {

        if (tasksRepository == null || tasksRepository.isEmpty()) {
            return Collections.emptyList();
        }

        return tasksRepository.stream()
                .sorted(Comparator.comparing(i -> i.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> sortByPeriodOfExecution() {

        if (tasksRepository == null || tasksRepository.isEmpty()) {
            return Collections.emptyList();
        }

        return tasksRepository.stream()
                .sorted(Comparator.comparing(i -> i.getPeriodOfExecution()))
                .collect(Collectors.toList());
    }

    @Override
    public Task updateTask(
            Long id,
            String newName,
            String newDescription,
            LocalDate newDate,
            Status newStatus
    ) {

        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }

        if (newName == null || newDescription.isEmpty()) {
            throw new IllegalArgumentException("Имя задачин может быть пустым");
        }

        if (newDescription.isBlank()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }

        if (newDate == null || newDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Срок задачи не может быть пустым или в прошлом");
        }

        //Ищем задачу
        Task task = tasksRepository.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Задача с ID " + id + "не найдена"));

        task.setNameTask(newName);
        task.setDescription(newDescription);
        task.setPeriodOfExecution(newDate);
        task.setStatus(newStatus);

        return task;
    }
}
