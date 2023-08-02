package com.example.task.data;
import org.springframework.data.repository.CrudRepository;

import com.example.task.User;

public interface UserRepository extends CrudRepository<User,Long>{
    User findByUsername(String username);
}
