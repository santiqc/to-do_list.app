package com.todolist.app.repository;


import com.todolist.app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Integer> {

}
