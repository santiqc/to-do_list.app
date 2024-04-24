package com.todolist.app.service;

import com.todolist.app.dto.RequestTaskDTO;
import com.todolist.app.dto.ResponseDTO;
import com.todolist.app.entity.Task;
import com.todolist.app.exception.TaskException;
import com.todolist.app.repository.TaskRepository;
import com.todolist.app.utils.Mensaje;
import com.todolist.app.utils.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateTaskTest() throws TaskException {

        Integer taskId = 1;
        RequestTaskDTO requestTaskDTO = new RequestTaskDTO();
        requestTaskDTO.setTitle("titulo actualizado");
        requestTaskDTO.setDescription("descripcion actualizada");
        requestTaskDTO.setDueDate(Timestamp.valueOf("2024-04-30 00:00:00"));
        requestTaskDTO.setStatus(Status.valueOf("P"));

        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setTitle("titulo");
        existingTask.setDescription("descripcion");
        existingTask.setDueDate(Timestamp.valueOf("2024-04-25 00:00:00"));
        existingTask.setStatus(Status.valueOf("C"));

        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);


        ResponseDTO responseDTO = taskService.updateTask(taskId, requestTaskDTO);


        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK.value(), responseDTO.getCodigoRespuesta());
        assertEquals("Tarea actualizada correctamente.", responseDTO.getMensaje());
        assertEquals("titulo actualizado", existingTask.getTitle());
        assertEquals("descripcion actualizada", existingTask.getDescription());
        assertEquals(Timestamp.valueOf("2024-04-30 00:00:00"), existingTask.getDueDate());
        assertEquals(Status.P, existingTask.getStatus());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTaskTestThrowsException() {
        Integer taskId = 1;
        RequestTaskDTO requestTaskDTO = new RequestTaskDTO();

        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.empty());

        assertThrows(TaskException.class, () -> taskService.updateTask(taskId, requestTaskDTO));
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void saveTaskTest() throws TaskException {
        Task task = new Task();
        when(taskRepository.save(task)).thenReturn(task);

        ResponseDTO result = taskService.saveTask(task);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED.value(), result.getCodigoRespuesta());
        assertEquals(Mensaje.TASK_GUARDADA, result.getMensaje());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void listTasksTest() throws TaskException {
        Page<Task> page = mock(Page.class);
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Task> result = taskService.listTasks(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(page, result);
        verify(taskRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getTaskByIdTest() throws TaskException {
        Task task = new Task();
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1);

        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    void deleteTaskTest() throws TaskException {
        Task task = new Task();
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        ResponseDTO result = taskService.deleteTask(1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK.value(), result.getCodigoRespuesta());
        assertEquals(Mensaje.TASK_ELIMINADA, result.getMensaje());
        verify(taskRepository, times(1)).deleteById(1);
    }


}
