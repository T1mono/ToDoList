package ru.javadaddy.enums;

public enum Status {
    TODO("К выполнению"),
    IN_PROGRESS("В процессе"),
    DONE("Завершено");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
