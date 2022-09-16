package ru.spacelord.todo.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.todo.todolist.dto.TaskDTO;
import ru.spacelord.todo.todolist.service.TaskService;


import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping({"/welcome","/"})
public class MainController {

    private final TaskService taskService;


    @Autowired
    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/today")
    public String today(Model model) {
        List<TaskDTO> list = taskService.getAllToday();
        String[] time = new String[]{LocalDateTime.now().getDayOfWeek().getDisplayName(TextStyle.SHORT,new Locale("ru")),
                        Integer.toString(LocalDateTime.now().getDayOfMonth()),
                        LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("ru"))};
        model.addAttribute("time", time);
        model.addAttribute("todo",new TaskDTO());
        model.addAttribute("tasks", list);
        model.addAttribute("taskCount",list.stream()
                .filter(task -> task.getTaskStatus().name().equals("ACTIVE"))
                .count());
        return "today-page";
    }

    @GetMapping("/all-tasks")
    public String allTasksPage(Model model) {
        List<TaskDTO> list = taskService.getAll();
        model.addAttribute("tasks", list);
        return "index-all";
    }

    @PostMapping("/new")
    public String addTask(@ModelAttribute("todo") TaskDTO taskDTO, Model model) {
        if (!taskService.save(taskDTO)) {
            model.addAttribute("todo", taskDTO);
        }
        return "redirect:/welcome/today";

    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable(value = "id") Long id) {
        taskService.delete(id);
        return "redirect:/welcome/today";
    }

    @GetMapping("/change/{id}")
    public String changeTask(@PathVariable(value = "id") Long id) {
        taskService.changeStatus(id);
        return "redirect:/welcome/today";
    }
}
