package ru.spacelord.todo.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spacelord.todo.todolist.dto.UserDTO;
import ru.spacelord.todo.todolist.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String login() {
        return "login";
    }

    @PostMapping("/auth")
    public String loginSuccess() {
        return "redirect:/welcome/today";
    }

    @GetMapping("/sign")
    public String sign(Model model) {
        model.addAttribute("user", new UserDTO());
        return "sign";
    }

    @PostMapping("/sign")
    public String saveUser(UserDTO userDTO, Model model) {
        if(userService.save(userDTO)) {
            return "redirect:/login";
        }
        else {
            model.addAttribute("user",userDTO);
            model.addAttribute("match",true);
            return "sign";
        }
    }
}
