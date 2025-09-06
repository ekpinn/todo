package com.example.todo.services;

import com.example.todo.models.TaskId;
import com.example.todo.models.TaskTask;
import com.example.todo.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskTaskRepository;


}
