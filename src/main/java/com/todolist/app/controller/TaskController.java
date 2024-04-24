package com.todolist.app.controller;

import com.todolist.app.dto.RequestTaskDTO;
import com.todolist.app.dto.ResponseDTO;
import com.todolist.app.entity.Task;
import com.todolist.app.exception.TaskException;
import com.todolist.app.service.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Task CRUD")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @Operation(summary = "Guardar tarea")
    @PostMapping("/tasks")
    @ApiResponse(responseCode = "201", description = "Tarea guardada correctamente")
    public ResponseEntity<ResponseDTO> saveTask(@RequestBody Task task) throws TaskException {
        return new ResponseEntity<>(taskService.saveTask(task), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener lista de tareas paginada")
    @GetMapping("/tasks")
    @ApiResponse(responseCode = "200", description = "Lista de tareas paginada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    public Page<Task> getTasks(
            @Parameter(description = "Número de página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskService.listTasks(pageable);
    }

    @Operation(summary = "Obtener tarea por ID")
    @GetMapping("/tasks/{id}")
    @ApiResponse(responseCode = "200", description = "Tarea encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "ID de la tarea") @PathVariable Integer id) throws TaskException {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar tarea")
    @PutMapping("/tasks/{id}")
    @ApiResponse(responseCode = "201", description = "Tarea actualizada correctamente")
    public ResponseEntity<ResponseDTO> updateTask(
            @Parameter(description = "ID de la tarea") @PathVariable Integer id,
            @RequestBody RequestTaskDTO task) throws TaskException {
        return new ResponseEntity<>(taskService.updateTask(id, task), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar tarea por ID")
    @DeleteMapping("/tasks/{id}")
    @ApiResponse(responseCode = "200", description = "Tarea eliminada correctamente")
    public ResponseEntity<ResponseDTO> deleteTask(
            @Parameter(description = "ID de la tarea") @PathVariable Integer id) throws TaskException {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }
}
