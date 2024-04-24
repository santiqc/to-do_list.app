package com.todolist.app.controller;


import com.todolist.app.dto.RequestTaskDTO;
import com.todolist.app.dto.ResponseDTO;
import com.todolist.app.entity.Task;
import com.todolist.app.exception.TaskException;
import com.todolist.app.service.ITaskService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<ResponseDTO> saveTask(@RequestBody Task task) throws TaskException {
        return new ResponseEntity<>(taskService.saveTask(task), HttpStatus.CREATED);
    }

    @GetMapping("/tasks")
    public Page<Task> getTasks(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskService.listTasks(pageable);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> saveTask(@PathVariable Integer id) throws TaskException {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable Integer id, @RequestBody RequestTaskDTO task) throws TaskException {
        return new ResponseEntity<>(taskService.updateTask(id, task), HttpStatus.CREATED);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ResponseDTO> deleteTask(@PathVariable Integer id) throws TaskException {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }


}
