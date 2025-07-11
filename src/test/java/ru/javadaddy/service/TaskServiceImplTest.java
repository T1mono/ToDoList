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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

        tasks = List.of(
                new Task(2L,
                        "Продолжить изучение коллекций Java",
                        "Структуру Map",
                        LocalDate.of(2025, 7, 16),
                        Status.TODO),
                new Task(3L,
                        "Продолжить изучение Git",
                        "Создание веток",
                        LocalDate.of(2025, 7, 20),
                        Status.IN_PROGRESS
                )
        );
    }

    @Test
    void testCreateTask() {
        taskService.createTask(task);
        verify(taskRepository).createTask(task);
    }

    @Test
    void testGetAllTask() {
        when(taskRepository.getTasks()).thenReturn(tasks);
        List<Task> result = taskService.getAllTask();
        assertEquals(tasks, result);
    }

    @Test
    void testDeleteTask() {
        taskService.deleteTask(task.getId());
        verify(taskRepository).removeTask(task.getId());
    }

    @Test
    void testFindByStatus() {
        when(taskRepository.filterTaskByStatus(task.getStatus())).thenReturn(tasks);
        List<Task> result = taskService.findByStatus(task.getStatus());
        verify(taskRepository).filterTaskByStatus(task.getStatus());
        assertEquals(tasks, result);
    }

    @Test
    void testSortByStatus() {
        when(taskRepository.sortByStatus()).thenReturn(tasks);
        List<Task> result = taskService.sortByStatus();
        verify(taskRepository).sortByStatus();
        assertEquals(tasks, result);
    }

    @Test
    void testFindByPeriodOfExecution() {
        when(taskRepository.sortByPeriodOfExecution()).thenReturn(tasks);
        List<Task> result = taskService.findByPeriodOfExecution();
        verify(taskRepository).sortByPeriodOfExecution();
        assertEquals(tasks, result);
    }

    @Test
    void updateTask() {
        when(taskRepository.updateTask(anyLong(), anyString(), anyString(), any(LocalDate.class), any(Status.class)))
                .thenReturn(task);

        Task result = taskService.updateTask(
                task.getId(),
                task.getNameTask(),
                task.getDescription(),
                task.getPeriodOfExecution(),
                task.getStatus()
        );
        verify(taskRepository).updateTask(
                task.getId(),
                task.getNameTask(),
                task.getDescription(),
                task.getPeriodOfExecution(),
                task.getStatus()
        );
        assertEquals(task, result);
    }
}