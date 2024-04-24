package com.todolist.app.service;

import com.todolist.app.dto.RequestTaskDTO;
import com.todolist.app.dto.ResponseDTO;
import com.todolist.app.entity.Task;
import com.todolist.app.exception.TaskException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ITaskService {

    ResponseDTO updateTask(Integer id, RequestTaskDTO task) throws TaskException;

    ResponseDTO saveTask(Task task) throws TaskException;

    Page<Task> listTasks(Pageable pageable) throws TaskException;

    Task getTaskById(Integer id) throws TaskException;

    ResponseDTO deleteTask(Integer idTask) throws TaskException;



}
