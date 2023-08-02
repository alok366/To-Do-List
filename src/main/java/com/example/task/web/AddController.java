package com.example.task.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.task.Task;
import com.example.task.User;
import com.example.task.data.TaskRepository;
import com.example.task.data.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/add")
@SessionAttributes("task")
@Controller
public class AddController {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public AddController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        return user;
    }

    @ModelAttribute(name = "task")
    public Task task() {

        return new Task();
    }

    @GetMapping
    public String addForm(Model model) {
        return "add";
    }

    @PostMapping
    public String processTask(@Valid Task task, Errors errors,
            SessionStatus sessionStatus,
            @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "add";
        }
        task.setUser(user);
        taskRepository.save(task);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
