package com.example.todo.controllers;

import com.example.todo.models.TaskId;
import com.example.todo.models.TaskTask;
import com.example.todo.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/account")
    public String toDoAccount(Model model){
        return "account";
    }

    @GetMapping("/todo")
    public String toDoCalendar(Model model){
        LocalDate now = LocalDate.now();

        int todayTasksCount=taskRepository.countByTaskId_YearAndTaskId_MonthAndTaskId_Day(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        model.addAttribute("todayTasksCount", todayTasksCount);
        return "todo";
    }

    @GetMapping("/todo/{dayId}")
    public String TaskOfDay(@PathVariable(value = "dayId") String dayId, Model model) {
        try {
            System.out.println("Получен dayId: " + dayId);

            if (!dayId.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                model.addAttribute("error", "Неверный формат даты");
                return "error";
            }

            String[] parts = dayId.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            Iterable<TaskTask> allTasksIterable = taskRepository.findAll();
            List<TaskTask> allTasks = new ArrayList<>();
            allTasksIterable.forEach(allTasks::add);
            List<TaskTask> dayTasks = allTasks.stream()
                    .filter(task -> task.getTaskId().getYear() == year &&
                            task.getTaskId().getMonth() == month &&
                            task.getTaskId().getDay() == day)
                    .toList();

            model.addAttribute("tasks", dayTasks);
            model.addAttribute("dayId", dayId);

            String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
            String formattedDate = day + " " + months[month-1] + " " + year + " года";
            model.addAttribute("formattedDate", formattedDate);

            return "task_of_day";

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке задач: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/task_of_day/{dayId}/add")
    public String AddingNewTask(@PathVariable String dayId,
                                @RequestParam String taskText,
                                @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime taskTime,
                                Model model) {
        try {
            if (taskText == null || taskText.trim().isEmpty()) {
                model.addAttribute("error", "Задача не может быть пустой");
                return "error";
            }

            String[] parts = dayId.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            TaskId taskId = new TaskId(year, month, day);

            TaskTask taskTask = new TaskTask(taskId, taskTime, taskText.trim());
            taskRepository.save(taskTask);

            return "redirect:/todo/" + dayId;

        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при создании задачи: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/task_of_day/{dayId}/delete/{id}")
    public String DeletionTask(@PathVariable Long id,
                               @PathVariable String dayId){
        try {
            TaskTask task=taskRepository.findById(id).orElseThrow();
            taskRepository.delete(task);
            return "redirect:/todo/"+dayId;
        } catch (Exception e){
            return "error";
        }
    }

    @PostMapping("/task_of_day/{dayId}/edit/{id}")
    public String ChangingTask(@PathVariable Long id,
                               @PathVariable String dayId, @RequestParam String taskText, @RequestParam LocalTime taskTime, Model model){
        try{
            TaskTask newTask=taskRepository.findById(id).orElseThrow();
            newTask.setTask(taskText);
            newTask.setTime(taskTime);
            taskRepository.save(newTask);
            return "redirect:/todo/"+dayId;
        } catch(Exception e){
            return "error";
        }
    }

    @GetMapping("/addTaskFromHomePage")
    public String HomePageButton(Model model){
        return "todo";
    }

    @GetMapping("/addTaskFromAccountPage")
    public String AccountPageButton(Model model){
        return "todo";
    }

    @ResponseBody
    @GetMapping("/api/tasks/count")
    public int getTasksCount(@RequestParam int year,
                             @RequestParam int month,
                             @RequestParam int day) {
        return taskRepository.countByTaskId_YearAndTaskId_MonthAndTaskId_Day(year, month, day);
    }

    @PostMapping("/task_of_day/{dayId}/toggle/{id}")
    @ResponseBody
    public ResponseEntity<String> toggleTaskStatus(@PathVariable Long id,
                                                   @RequestParam boolean completed) {
        try {
            TaskTask task = taskRepository.findById(id).orElseThrow();
            task.setRealization(completed);
            taskRepository.save(task);
            return ResponseEntity.ok("Status updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating status: " + e.getMessage());
        }
    }
}
