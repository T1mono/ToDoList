package ru.javadaddy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import ru.javadaddy.enums.Status;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {
    private Long id;
    private String nameTask;
    private String description;
    private LocalDate periodOfExecution;
    private Status status;
}
