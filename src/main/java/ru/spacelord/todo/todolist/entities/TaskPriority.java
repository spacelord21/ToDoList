package ru.spacelord.todo.todolist.entities;

public enum TaskPriority {

    PRIORITY(1),SECONDARY(2),UNIMPORTANT(3);

    private final int graduate;

    TaskPriority(final int graduate) {
        this.graduate = graduate;
    }

    public int getGraduate() {
        return this.graduate;
    }


}
