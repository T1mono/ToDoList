package ru.javadaddy.controller;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;
import ru.javadaddy.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TaskController {

    private final Scanner scanner;

    private final TaskService taskService;

    private Status status;

    public TaskController(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }

    private List<Task> createTask() {
        return List.of(
                new Task(
                        1L,
                        "Прочитать статью",
                        "2 страницы",
                        LocalDate.now(),
                        Status.TODO
                ),
                new Task(
                        2L,
                        "Помыть машину",
                        "На автомой на Ленина 45",
                        LocalDate.of(2025, 07, 10),
                        Status.IN_PROGRESS),
                new Task(
                        3L,
                        "Забрать заказ на Ozon",
                        "Ozon который возле работы",
                        LocalDate.now(),
                        Status.DONE)
        );
    }

    public void run() {

    }

    //TODO: Показать список задач
    private void showToDoList() {
        List<Task> tasks = taskService.getAllTask();
        if (tasks.isEmpty()) {
            System.out.println("Ваш список задач пуст");
            return;
        }

        System.out.println("===Ваш список задач===");
        System.out.println("ID  Название          Описание        Срок        Статус");
        System.out.println("--------------------------------------------------------");

        tasks.forEach(task -> System.out.printf(
                "%-3d %-16s %-15s %-11s %s%n",
                task.getId(),
                task.getNameTask(),
                task.getDescription(),
                task.getPeriodOfExecution(),
                task.getStatus())
        );
    }

    //TODO: Добавить задачу в TODO List
    private void addTask() {

    }

    //TODO: Удалить задачу
    private void deleteTask() {

    }

    //TODO: Отфильтровать задачу
    private void filterTaskByStatus() {

    }

    //TODO: Отсортировать задачи по статусу
    private void sortByStatus() {

    }

    private void showCommandToDoList() {
        System.out.println("\n=== TODO ==");
        System.out.println("1. add - добавить задачу");
        System.out.println("2. list – вывести список задач");
        System.out.println("3. edit – редактировать задачу");
        System.out.println("4. delete – удалить задачу");
        System.out.println("5. filter – отфильтровать задачи по статусу.");
        System.out.println("6. sort – отсортировать задачи");
        System.out.println("0. exit – выход из системы");
        System.out.print("Выберите действие: ");
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}