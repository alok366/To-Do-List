package com.example.task.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.task.Task;
import com.example.task.data.TaskRepository;

@Controller
@RequestMapping("/finished")
public class finishedController {
    
    private TaskRepository taskRepository;

    @Autowired
    public finishedController(TaskRepository taskRepository)
    {
        this.taskRepository=taskRepository;
    }
    @GetMapping
    public String getFinishedTasks(Model model) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        List<Task> finishedTasks = taskRepository.findByUserUsernameAndCompletedTrue(username);
        model.addAttribute("tasks", finishedTasks);
        return "finished";
    }
}
