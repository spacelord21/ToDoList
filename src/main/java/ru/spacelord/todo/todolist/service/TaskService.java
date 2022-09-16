package ru.spacelord.todo.todolist.service;


import ru.spacelord.todo.todolist.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    boolean save(TaskDTO taskDTO);
    List<TaskDTO> getAllToday();
    List<TaskDTO> getAll();
    void delete(Long id);
    void changeStatus(Long id);
}
