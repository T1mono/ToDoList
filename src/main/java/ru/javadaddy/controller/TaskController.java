package ru.javadaddy.controller;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;
import ru.javadaddy.service.TaskService;
import ru.javadaddy.service.TaskServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskController {

    private final Scanner scanner;

    private final TaskService taskService;

    private Status status;

    public TaskController() {
        this.scanner = new Scanner(System.in);
        this.taskService = new TaskServiceImpl();
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
        while (true) {
            showCommandToDoList();
            int choice = readIntInput();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> showToDoList();
                case 3 -> deleteTask();
                case 4 -> filterTaskByStatus();
                case 5 -> sortByStatus();
            }
        }
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

        //Ввод ID задачи
        System.out.print("Введите ID задачи: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        //Ввод название задачи
        System.out.print("Введите название задачи: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Название задачи не может быть пусты");
            return;
        }

        //Ввод описания задачи
        System.out.print("Введите описание задачи: ");
        String description = scanner.nextLine();


        //Ввод даты
        LocalDate date = inputLocalDate();

        // Ввод статуса
        Status status = inputStatus();

        Task task = new Task(id, name, description, date, status);

        try {
            taskService.createTask(task);
            System.out.println("Задача добавлена в список");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Status inputStatus() {
        while (true) {
            System.out.println("Доступные статусы:");
            Arrays.stream(Status.values()).forEach(s -> System.out.println("- " + s));
            System.out.print("Введите статус: ");

            try {
                return Status.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный статус! Попробуйте снова");
            }
        }
    }

    private LocalDate inputLocalDate() {
        LocalDate date;
        while (true) {
            System.out.print("Введите дату выполнения (ГГГГ-ММ-ДД): ");
            String dateInput = scanner.nextLine();
            try {
                date = LocalDate.parse(dateInput);
                break; //Если дата корректная выходим из цикла
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты! Пример: 2025-07-07");
            }
        }
        return date;
    }

    //TODO: Удалить задачу
    private void deleteTask() {

        try {
            System.out.print("Введите ID задачи, чтобы удалить: ");
            Long id = scanner.nextLong();

            scanner.nextLine();

            taskService.deleteTask(id);
            System.out.println("Задача " + id + " удалена");
        } catch (InputMismatchException e) {
            System.out.println("Нужно ввести число!");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    //TODO: Отфильтровать задачу
    private void filterTaskByStatus() {
        //Показываем доступные статусы
        System.out.println("Доступные статусы: ");
        Arrays.stream(Status.values())
                .forEach(status -> System.out.println("- " + status));

        //Запрашиваем ввод необходимого статуса
        System.out.println("Введите статус задачи по которому хотите отфильтровать: ");
        String input = scanner.nextLine().toUpperCase();

        try {
            //Парсим вводимое значение в Enum
            Status filterStatus = Status.valueOf(input);

            //Сама фильтрация
            List<Task> filtered = taskService.findByStatus(filterStatus);

            if (filtered.isEmpty()) {
                System.out.println("Задачи со статус не найдены");
            } else {
                System.out.println("=== Задачи со статусом " + filterStatus + " не найдены ===");
                filtered.forEach(task -> System.out.printf(
                        "[ID: %d] %s (до %s)%n",
                        task.getId(),
                        task.getNameTask(),
                        task.getPeriodOfExecution()
                ));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный статус! Доступные: " + Arrays.toString(Status.values()));
        }
    }

    //TODO: Отсортировать задачи по статусу
    private void sortByStatus() {

        List<Task> sortedTasks = taskService.sortByStatus();

        if (sortedTasks.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }

        System.out.println("=== Задачи, отсортированные по статусу ===");
        sortedTasks.forEach(task -> System.out.printf(
                "[%s] %s (до %s)%n",
                task.getStatus(),
                task.getNameTask(),
                task.getPeriodOfExecution()
        ));
    }

    private void showCommandToDoList() {
        System.out.println("\n=== TODO ===");
        System.out.println("1. add - добавить задачу");
        System.out.println("2. list – вывести список задач");
        System.out.println("3. edit – редактировать задачу");
        System.out.println("4. delete – удалить задачу");
        System.out.println("5. filter – отфильтровать задачи по статусу.");
        System.out.println("6. sort – отсортировать задачи");
        System.out.println("0. exit – выход из системы");
        System.out.print("Выберите действие: ");
    }

    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Введите нужный номер команды для работы со списком задач: ");
            scanner.next();
        }

        int input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }

    public static void main(String[] args) {
      TaskController taskController = new TaskController();
      taskController.run();
    }
}