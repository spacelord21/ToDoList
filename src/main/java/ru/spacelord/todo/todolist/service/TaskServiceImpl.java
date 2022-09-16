package ru.spacelord.todo.todolist.service;

import org.apache.tomcat.jni.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.spacelord.todo.todolist.dao.TaskRepository;
import ru.spacelord.todo.todolist.dao.UserRepository;
import ru.spacelord.todo.todolist.dto.TaskDTO;
import ru.spacelord.todo.todolist.entities.TaskEntity;
import ru.spacelord.todo.todolist.entities.TaskStatus;
import ru.spacelord.todo.todolist.entities.UserEntity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(TaskDTO taskDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findFirstByUsername(auth.getName());
        TaskEntity task;
        if(!taskDTO.getTimeCreated().isEmpty() && !taskDTO.getTimeCompleted().isEmpty()
            && !Objects.equals(taskDTO.getDate(), "")) {
            String[] createTime = taskDTO.getTimeCreated().split(":");
            String[] completeTime = taskDTO.getTimeCompleted().split(":");
            task = TaskEntity.builder()
                    .goal(taskDTO.getGoal())
                    .description(taskDTO.getDescription())
                    .taskStatus(TaskStatus.ACTIVE)
                    .taskPriority(taskDTO.getTaskPriority())
                    .userEntity(user)
                    .date(LocalDate.parse(taskDTO.getDate()))
                    .createdTime(LocalTime.of(Integer.parseInt(createTime[0]),Integer.parseInt(createTime[1])))
                    .completedTime(LocalTime.of(Integer.parseInt(completeTime[0]),Integer.parseInt(completeTime[1])))
                    .build();
        }
        else {
            task = TaskEntity.builder()
                    .goal(taskDTO.getGoal())
                    .createdTime(null)
                    .completedTime(null)
                    .taskStatus(TaskStatus.ACTIVE)
                    .userEntity(user)
                    .taskPriority(taskDTO.getTaskPriority())
                    .description(taskDTO.getDescription())
                    .date(null)
                    .build();
        }
        taskRepository.save(task);
        return true;
    }

    @Override
    public List<TaskDTO> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findFirstByUsername(auth.getName());
        return taskRepository.getAllByUserEntity(user)
                .stream()
                .map(this::toDto)
//                .sorted(Comparator.comparing(taskDTO -> taskDTO.toLocalDate(taskDTO.getDate())))
//                .sorted(Comparator.comparing(taskDTO -> taskDTO.toLocalTime(taskDTO.getTimeCreated())))
                .sorted(Comparator.comparing(taskDTO -> taskDTO.getTaskPriority().getGraduate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllToday() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findFirstByUsername(auth.getName());
        return taskRepository.getAllByUserEntity(user)
                .stream()
                .map(this::toDto)
                //TODO сортировка по времени
                .sorted(Comparator.comparing(taskDTO -> taskDTO.getTaskPriority().getGraduate()))
//                .filter(taskDTO -> Objects.equals(taskDTO.toLocalDate(taskDTO.getDate()), LocalDate.now()))
                .filter(taskDTO -> taskDTO.getTaskStatus().name().equals("ACTIVE"))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id) {
        taskRepository.turnStatus(id);
    }


    private TaskDTO toDto(TaskEntity taskEntity) {
        return TaskDTO.builder()
                .id(taskEntity.getId())
                .goal(taskEntity.getGoal())
                .taskStatus(taskEntity.getTaskStatus())
                .timeCreated((taskEntity.getCreatedTime()!=null)?taskEntity.getCreatedTime().toString():null)
                .timeCompleted((taskEntity.getCreatedTime()!=null)?taskEntity.getCompletedTime().toString():null)
                .taskPriority(taskEntity.getTaskPriority())
                .description(taskEntity.getDescription())
                .date((taskEntity.getDate()!=null)?taskEntity.getDate().toString():null)
                .build();
    }
}
