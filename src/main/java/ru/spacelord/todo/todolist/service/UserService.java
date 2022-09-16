package ru.spacelord.todo.todolist.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.spacelord.todo.todolist.dto.UserDTO;


public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
}
