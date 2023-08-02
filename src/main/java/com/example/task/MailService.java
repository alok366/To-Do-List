package com.example.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.task.data.TaskRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MailService {

    private  JavaMailSender javaMailSender;
    private  TaskRepository taskRepository;
    private Logger logger=LoggerFactory.getLogger(MailService.class);

    @Autowired
    public MailService(JavaMailSender javaMailSender, TaskRepository taskRepository) {
        this.javaMailSender = javaMailSender;
        this.taskRepository = taskRepository;
    }

    // Method to send an email for an overdue task
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendOverdueTaskEmail(Task task,String userEmail, String taskTitle,String description) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //task.setReminderEmailSent(true);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Overdue Task Reminder");
        mailMessage.setText("Hello,\nYour task \"" + taskTitle + "\" is overdue."+"\n\nDescription: "+description);
        taskRepository.updateReminderEmailSent(task.getId());

        javaMailSender.send(mailMessage);
        try {
        javaMailSender.send(mailMessage);
        taskRepository.updateReminderEmailSent(task.getId());
    } catch (MailException e) {
        logger.info("mail not sent");
    }
    }

    // Method to check for overdue tasks and send emails
    @Scheduled(fixedRate = 3600000) // Run every hour (adjust as needed)
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkAndSendOverdueTaskEmails() {
        
        
        LocalDateTime now = LocalDateTime.now();
        List<Task> overdueTasks = taskRepository.findOverdueTasks(now);
        if (overdueTasks == null || overdueTasks.isEmpty()) {
            // No overdue tasks, return early
            return;
        }
        for (Task task : overdueTasks) {
            if (!task.isReminderEmailSent()) {
                sendOverdueTaskEmail(task,task.getUser().getEmail(), task.getTitle(),task.getDescription());
            }
        }
    }
}

