package ru.spacelord.todo.todolist.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spacelord.todo.todolist.dao.UserRepository;
import ru.spacelord.todo.todolist.dto.UserDTO;
import ru.spacelord.todo.todolist.entities.Role;
import ru.spacelord.todo.todolist.entities.TaskEntity;
import ru.spacelord.todo.todolist.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean save(UserDTO userDTO) {
        if(userRepository.findFirstByUsername(userDTO.getUsername())!=null) {
            System.out.println("Такое имя уже есть, выберите другое!");
            return false;
        }
        else if(userRepository.findFirstByEmail(userDTO.getEmail())!=null) {
            System.out.println("Аккаунт с этой почтой уже существует!");
            return false;
        }
        else {
            if(userDTO.getPassword().equals(userDTO.getMatchingPassword())) {
                UserEntity user = UserEntity.builder()
                        .username(userDTO.getUsername())
                        .email(userDTO.getEmail())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .role(Role.USER)
                        .purposeNumber(5)
                        .build();
                userRepository.save(user);
                return true;
            }
            else {
                System.out.println("Пароли не совпадают");
                return false;
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user;
        if(username.contains("@")) {
            user = userRepository.findFirstByEmail(username);
        }
        else {
            user = userRepository.findFirstByUsername(username);
        }
        if(user == null) {
            System.out.println("Я не нашел юзера-арбузера" + username);
            throw new UsernameNotFoundException("User not found with username:" + username);
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new User(user.getUsername(),user.getPassword(),roles);
    }

    public void changePassword(String newPassword,Long id) {
        userRepository.changePassword(passwordEncoder.encode(newPassword),id);
    }
}
