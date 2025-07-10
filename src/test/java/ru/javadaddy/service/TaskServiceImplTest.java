package ru.javadaddy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javadaddy.enums.Status;
import ru.javadaddy.model.Task;
import ru.javadaddy.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    private List<Task> tasks;


    @BeforeEach
    void setUp() {
        task = new Task(1L,
                "Продолжить изучение SQL",
                "Шардирование",
                LocalDate.of(2025, 7, 15),
                Status.TODO
        );

        tasks = List.of(new Task(2L,
                        "Продолжить изучение коллекций Java",
                        "Структуру Map",
                        LocalDate.of(2025, 7, 16),
                        Status.TODO),
                new Task(3L,
                        "Продолжить изучение Git",
                        "Создание веток",
                        LocalDate.of(2025, 7, 20),
                        Status.IN_PROGRESS
                ));
    }

    @Test
    void testCreateTask() {
        taskService.createTask(task);
        verify(taskRepository).createTask(task);
    }

    @Test
    void getAllTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void findByStatus() {
    }

    @Test
    void sortByStatus() {
    }

    @Test
    void findByPeriodOfExecution() {
    }

    @Test
    void updateTask() {
    }
}