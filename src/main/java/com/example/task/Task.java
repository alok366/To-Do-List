package com.example.task;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@DynamicUpdate
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 5, max = 30)
    private String title;

    @NonNull
    @Size(min = 10, max = 100)
    private String description;

    private LocalDateTime placedDate;

    @ManyToOne
    private User user;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;

    private boolean completed;
    private boolean reminderEmailSent;

    public enum Type {
        DEFAULT, PERSONAL, SHOPPING, WORK
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    public Task() {
        this.title = "";
        this.description = "";
        this.placedDate = LocalDateTime.of(2024, 8, 15, 12, 30, 0);
        this.completed = false;
        this.type = Type.DEFAULT;
        this.reminderEmailSent=false;
    }

    // Parameterized constructor
    public Task(String title, String description, LocalDateTime duDate) {
        this.title = title;
        this.description = description;
        this.placedDate = LocalDateTime.now();
        this.dueDate = duDate;
        this.completed = false;
    }

    // Getters and Setters

    public boolean isReminderEmailSent() {
        return reminderEmailSent;
    }

    public void setReminderEmailSent(boolean reminderEmailSent) {
        this.reminderEmailSent = reminderEmailSent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(LocalDateTime placedDate) {
        this.placedDate = placedDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @PrePersist
    public void prePersist() {
        this.placedDate = LocalDateTime.now();
        this.completed = false;
    }
}
