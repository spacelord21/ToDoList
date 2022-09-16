package ru.spacelord.todo.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spacelord.todo.todolist.service.TaskService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/my-completed-task")
public class CompletedController {

    private final TaskService taskService;

    @Autowired
    public CompletedController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String taskList(Model model) {
        model.addAttribute("tasks", taskService.getAll()
                .stream().filter(taskDTO -> taskDTO.getTaskStatus().name().equals("COMPLETED"))
                .collect(Collectors.toList()));
        return "task-completed-page";
    }
}
