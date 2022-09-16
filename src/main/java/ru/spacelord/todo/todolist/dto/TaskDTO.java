package ru.spacelord.todo.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spacelord.todo.todolist.entities.TaskPriority;
import ru.spacelord.todo.todolist.entities.TaskStatus;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String goal;
    private String timeCreated;
    private String timeCompleted;
    private TaskStatus taskStatus;
    private String date;
    private String description;
    private TaskPriority taskPriority;


    public boolean comparingTimesForExpired() {
        if(!getTimeCompleted().isEmpty() && LocalDate.parse(getDate()).isEqual(LocalDate.now())) {
            String[] currentTime = getTimeCompleted().split(":");
            LocalTime timeNow = LocalTime.now();
            return timeNow.isAfter(LocalTime.of(Integer.parseInt(currentTime[0]),
                    Integer.parseInt(currentTime[1])));
        }
        else {
            return false;
        }
    }

    public boolean comparingTimesForWarning() {
        if(!getTimeCompleted().isEmpty()) {
            String[] currentTime = getTimeCompleted().split(":");
            LocalTime timeNow = LocalTime.now();
            return timeNow.isAfter(LocalTime.of(Integer.parseInt(currentTime[0]),
                    Integer.parseInt(currentTime[1])).minusMinutes(30));
        }
        else {
            return false;
        }
    }

    public LocalTime toLocalTime(String time) {
        String[] createTime = time.split(":");
        return LocalTime.of(Integer.parseInt(createTime[0]),Integer.parseInt(createTime[1]));
    }

    public LocalDate toLocalDate(String date) {
        String[] currDate = date.split("-");
        return LocalDate.of(Integer.parseInt(currDate[0]),Integer.parseInt(currDate[1]),Integer.parseInt(currDate[2]));
    }
}
