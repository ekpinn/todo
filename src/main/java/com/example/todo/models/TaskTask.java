package com.example.todo.models;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class TaskTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private TaskId taskId;
    private LocalTime time;
    private String task;
    private boolean realization;

    public TaskTask() {
    }

    public TaskTask(TaskId taskId, LocalTime time, String task) {
        this.taskId = taskId;
        this.time = time;
        this.task = task;
        this.realization=false;
    }

    public TaskTask(Long id, TaskId taskId, LocalTime time, String task, boolean realization) {
        this.id = id;
        this.taskId = taskId;
        this.time = time;
        this.task = task;
        this.realization = realization;
    }

    public Long getId() {
        return id;
    }

    public TaskId getTaskId() {
        return taskId;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getTask() {
        return task;
    }

    public boolean isRealization() {
        return realization;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskId(TaskId taskId) {
        this.taskId = taskId;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setRealization(boolean realization) {
        this.realization = realization;
    }
}

