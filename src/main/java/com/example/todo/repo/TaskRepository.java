package com.example.todo.repo;

import com.example.todo.models.TaskId;
import com.example.todo.models.TaskTask;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskTask, Long> {
    @Query("SELECT COUNT(t) FROM TaskTask t WHERE t.taskId.year = :year AND t.taskId.month = :month AND t.taskId.day = :day")
    int countByTaskId_YearAndTaskId_MonthAndTaskId_Day(@Param("year") int year,
                                                       @Param("month") int month,
                                                       @Param("day") int day);

    @Query("SELECT t.taskId.day, COUNT(t) FROM TaskTask t WHERE t.taskId.year = :year AND t.taskId.month = :month GROUP BY t.taskId.day")
    List<Object[]> getDailyTasksCount(@Param("year") int year, @Param("month") int month);
}
