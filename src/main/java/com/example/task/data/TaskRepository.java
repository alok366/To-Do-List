package com.example.task.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.task.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByType(Task.Type type);

    List<Task> findByCompletedTrue();

    List<Task> findByUserUsernameAndCompletedTrue(String username);

    @Modifying
    @Query("UPDATE Task t SET t.completed = :completed WHERE t.id = :taskId")
    void updateCompleted(@Param("taskId") Long taskId, @Param("completed") boolean completed);

    List<Task> findByUserUsernameAndTypeAndCompleted(String username, Task.Type type, boolean completed);

    List<Task> findByUserUsernameAndTitleContainingIgnoreCase(String username, String query);

    @Query("SELECT t FROM Task t WHERE t.dueDate <= :now AND t.completed = false")
    List<Task> findOverdueTasks(LocalDateTime now);

    @Modifying
    @Query("UPDATE Task t SET t.reminderEmailSent = true WHERE t.id = :taskId")
    void updateReminderEmailSent(Long taskId);
}
