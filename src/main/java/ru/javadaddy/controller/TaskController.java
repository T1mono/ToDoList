package ru.javadaddy.controller;

import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;
import ru.javadaddy.repository.TaskRepository;
import ru.javadaddy.repository.TaskRepositoryImpl;
import ru.javadaddy.service.TaskService;
import ru.javadaddy.service.TaskServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskController {
    private final Scanner scanner;
    private final TaskService taskService;

    // Внедряем TaskService через конструктор
    public TaskController(TaskService taskService) {
        this.scanner = new Scanner(System.in);
        this.taskService = taskService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            try {
                showCommandToDoList();
                int choice = readIntInput();

                switch (choice) {
                    case 1 -> addTask();
                    case 2 -> showToDoList();
                    case 3 -> editTask();
                    case 4 -> deleteTask();
                    case 5 -> filterTaskByStatus();
                    case 6 -> sortByStatus();
                    case 0 -> running = false;
                    default -> System.out.println("Неверная команда, попробуйте снова!");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                scanner.nextLine();
            }
        }
        System.out.println("Выход из приложения");
    }

    private void editTask() {
        System.out.println("Введите ID задачи для изменения: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Task> existingTask = taskService.getAllTask().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();

        if (existingTask.isEmpty()) {
            throw new IllegalArgumentException("Задача с ID " + id + " не найдена!");
        }

        System.out.println("Введите новое название задачи (текущее: " +
                existingTask.get().getNameTask() + "): ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }

        System.out.println("Введите новое описание задачи (текущее: " +
                existingTask.get().getDescription() + "): ");
        String newDescription = scanner.nextLine().trim();
        if (newDescription.isEmpty()) {
            newDescription = existingTask.get().getDescription();
        }

        System.out.println("Введите новый срок задачи (текущее: " +
                existingTask.get().getPeriodOfExecution() + "): ");
        LocalDate newDate = parseDate(scanner.nextLine());
        if (newDate == null || newDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Срок задачи не может быть в прошлом");
        }

        System.out.println("Доступные статусы: " + Arrays.toString(Status.values()));
        System.out.println("Введите новый статус задачи (текущее: " +
                existingTask.get().getStatus() + "): ");
        Status newStatus = Status.valueOf(scanner.nextLine().toUpperCase());

        Task updatedTask = taskService.updateTask(id, newName, newDescription, newDate, newStatus);
        System.out.println("Задача успешно обновлена!\n" + updatedTask);
    }

    private LocalDate parseDate(String dateInput) {
        try {
            return LocalDate.parse(dateInput);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный формат даты! Пример: 2025-07-07");
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
        System.out.println("Введите ID задачи: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Введите название задачи: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }

        System.out.println("Введите описание задачи: ");
        String description = scanner.nextLine().trim();

        System.out.println("Введите срок задачи (ГГГГ-ММ-ДД): ");
        LocalDate date = parseDate(scanner.nextLine());
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Срок задачи не может быть в прошлом");
        }

        System.out.println("Доступные статусы: " + Arrays.toString(Status.values()));
        System.out.println("Введите статус задачи: ");
        Status status = Status.valueOf(scanner.nextLine().toUpperCase());

        Task task = new Task(id, name, description, date, status);
        taskService.createTask(task);
        System.out.println("Задача добавлена в список");
    }

    //TODO: Удалить задачу
    private void deleteTask() {
        System.out.println("Введите ID задачи для удаления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        taskService.deleteTask(id);
        System.out.println("Задача " + id + " удалена");
    }

    //TODO: Отфильтровать задачу
    private void filterTaskByStatus() {
        System.out.println("Доступные статусы: " + Arrays.toString(Status.values()));
        System.out.println("Введите статус для фильтрации: ");
        Status filterStatus = Status.valueOf(scanner.nextLine().toUpperCase());

        List<Task> filtered = taskService.findByStatus(filterStatus);
        if (filtered.isEmpty()) {
            System.out.println("Задачи со статусом не найдены");
        } else {
            System.out.println("=== Задачи со статусом " + filterStatus + " ===");
            filtered.forEach(task -> System.out.printf(
                    "[ID: %d] %s (до %s)%n",
                    task.getId(),
                    task.getNameTask(),
                    task.getPeriodOfExecution()
            ));
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
        TaskRepository repository = new TaskRepositoryImpl();
        TaskService service = new TaskServiceImpl(repository);
        TaskController controller = new TaskController(service);
        controller.run();
    }
}