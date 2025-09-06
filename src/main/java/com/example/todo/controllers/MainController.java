package com.example.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String toDoMain(Model model){
        return "home";
    }

    @GetMapping("/home")
    public String toDoMain1(Model model){
        return "home";
    }

}
