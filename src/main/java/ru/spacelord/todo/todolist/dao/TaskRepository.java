package ru.spacelord.todo.todolist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.todo.todolist.entities.TaskEntity;
import ru.spacelord.todo.todolist.entities.UserEntity;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> getAllByUserEntity(UserEntity userEntity);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEntity SET taskStatus='COMPLETED' where id= :id")
    void turnStatus(Long id);


}
