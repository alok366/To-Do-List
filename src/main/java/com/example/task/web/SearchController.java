package com.example.task.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.task.Task;
import com.example.task.data.TaskRepository;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final TaskRepository taskRepository;
    
    @Autowired
    public SearchController(TaskRepository taskRepository)
    {
        this.taskRepository=taskRepository;
    }
    
    @GetMapping
    public String searchTasks(@RequestParam("query") String query, Model model) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        List<Task> searchResults = taskRepository.findByUserUsernameAndTitleContainingIgnoreCase(username,query);
        model.addAttribute("tasks", searchResults);
        model.addAttribute("query", query);
        return "search"; 
    }
}
