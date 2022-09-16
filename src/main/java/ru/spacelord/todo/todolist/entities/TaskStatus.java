package ru.spacelord.todo.todolist.entities;

public enum TaskStatus {
    ACTIVE{
        public String getName() {
            return "Задача активна";
        }
    },COMPLETED{
        @Override
        public String getName() {
            return "Задача выполнена!";
        }
    };

    public abstract String getName();
}
