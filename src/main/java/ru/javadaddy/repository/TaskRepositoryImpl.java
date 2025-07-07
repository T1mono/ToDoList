package ru.javadaddy.repository;

import lombok.extern.slf4j.Slf4j;
import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class TaskRepositoryImpl {
    List<Task> tasksRepository = new ArrayList<>();

    Task task = new Task();

    //Создание задачи
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
    public List<Task> getTasks() {
        if (tasksRepository == null || tasksRepository.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(tasksRepository);
    }

    //Удалить задачу
    public void removeTask(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }

        tasksRepository.removeIf(i -> i.getId().equals(id));
    }

    //Фильтровать задачи по статусу
    //TODO: Передать на стрим, когда будет время и доработать проверки списка задач
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

    public List<Task> sortByStatus() {

        if (tasksRepository == null || tasksRepository.isEmpty()) {
            return Collections.emptyList();
        }

        return tasksRepository.stream()
                .sorted(Comparator.comparing(i -> i.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Task> sortByPeriodOfExecution() {

        if (tasksRepository == null || tasksRepository.isEmpty()) {
            return Collections.emptyList();
        }

        return tasksRepository.stream()
                .sorted(Comparator.comparing(i -> i.getPeriodOfExecution()))
                .collect(Collectors.toList());
    }
}
