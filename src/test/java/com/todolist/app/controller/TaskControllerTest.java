package com.todolist.app.controller;

import com.todolist.app.dto.RequestTaskDTO;
import com.todolist.app.dto.ResponseDTO;
import com.todolist.app.entity.Task;
import com.todolist.app.exception.TaskException;
import com.todolist.app.service.ITaskService;
import com.todolist.app.utils.Mensaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TaskControllerTest {
    @Mock
    private ITaskService taskService;


    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTaskTest() throws TaskException {
        Task task = new Task();
        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setCodigoRespuesta(HttpStatus.CREATED.value());
        expectedResponse.setMensaje(Mensaje.TASK_GUARDADA);
        when(taskService.saveTask(task)).thenReturn(expectedResponse);

        ResponseEntity<ResponseDTO> result = taskController.saveTask(task);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
        verify(taskService, times(1)).saveTask(task);
    }

    @Test
    void getTasksTest() throws TaskException {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> expectedPage = mock(Page.class);
        when(taskService.listTasks(pageable)).thenReturn(expectedPage);

        Page<Task> result = taskController.getTasks(page, size);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(taskService, times(1)).listTasks(pageable);
    }

    @Test
    void getTaskByIdTest() throws TaskException {
        int taskId = 1;
        Task expectedTask = mock(Task.class);
        when(taskService.getTaskById(taskId)).thenReturn(expectedTask);

        ResponseEntity<Task> result = taskController.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedTask, result.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    void updateTaskTest() throws TaskException {
        int taskId = 1;
        RequestTaskDTO requestTaskDTO = new RequestTaskDTO();
        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setCodigoRespuesta(HttpStatus.CREATED.value());
        expectedResponse.setMensaje(Mensaje.TASK_ACTUALIZADA);
        when(taskService.updateTask(taskId, requestTaskDTO)).thenReturn(expectedResponse);

        ResponseEntity<ResponseDTO> result = taskController.updateTask(taskId, requestTaskDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
        verify(taskService, times(1)).updateTask(taskId, requestTaskDTO);
    }

    @Test
    void deleteTaskTest() throws TaskException {
        int taskId = 1;
        ResponseDTO expectedResponse = new ResponseDTO();
        expectedResponse.setCodigoRespuesta(HttpStatus.OK.value());
        expectedResponse.setMensaje(Mensaje.TASK_ELIMINADA);
        when(taskService.deleteTask(taskId)).thenReturn(expectedResponse);

        ResponseEntity<ResponseDTO> result = taskController.deleteTask(taskId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
        verify(taskService, times(1)).deleteTask(taskId);
    }

}
