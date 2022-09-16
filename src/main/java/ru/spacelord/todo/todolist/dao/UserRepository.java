package ru.spacelord.todo.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.todo.todolist.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findFirstByUsername(String name);
    UserEntity findFirstByEmail(String email);
    UserEntity getByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity SET password=:newPassword where id= :id")
    void changePassword(String newPassword,Long id);
}
