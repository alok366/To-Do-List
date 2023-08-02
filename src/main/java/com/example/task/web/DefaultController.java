package com.example.task.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.task.Task;
import com.example.task.data.TaskRepository;



@Controller
@RequestMapping("/default")
public class DefaultController {
    private TaskRepository taskRepository;

    @Autowired
    public DefaultController(TaskRepository taskRepository)
    {
        this.taskRepository=taskRepository;
    }
    
    @GetMapping
    public String getDefaultTasks(Model model) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        List<Task> defaultTasks=taskRepository.findByUserUsernameAndTypeAndCompleted(username, Task.Type.DEFAULT, false);
        model.addAttribute("tasks", defaultTasks);
        return "default";
    }

    @PostMapping("/updateCompleted")
    @Transactional
    public String updateCompleted(@RequestParam("id") Long taskId, @RequestParam("completed") Boolean completed) {
        taskRepository.updateCompleted(taskId, completed);
        return "redirect:/default"; 
    }
}
