package com.todolist.app.service;


import com.todolist.app.dto.RequestTaskDTO;
import com.todolist.app.dto.ResponseDTO;
import com.todolist.app.entity.Task;
import com.todolist.app.exception.TaskException;
import com.todolist.app.repository.TaskRepository;
import com.todolist.app.utils.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class TaskService implements ITaskService {

    private final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ResponseDTO updateTask(Integer id, RequestTaskDTO task) throws TaskException {
        LOGGER.info("El id de la tarea para actualizar es : {}", id);
        try {
            if (id == null) {
                throw new TaskException(Mensaje.IDENTIFICADOR_NULL);
            }

            Task taskFound = getTaskById(id);
            LOGGER.info("Tarea encontrada para actualizar : {}", taskFound);
            if (taskFound == null) {
                throw new TaskException(Mensaje.TASK_NO_ENCONTRADA_ACTUALIZARLA);
            }
            ResponseDTO responseDTO = new ResponseDTO();
            taskFound.setTitle(task.getTitle());
            taskFound.setDescription(task.getDescription());
            taskFound.setDueDate(task.getDueDate());
            taskFound.setStatus(task.getStatus());

            taskRepository.save(taskFound);

            responseDTO.setCodigoRespuesta(HttpStatus.OK.value());
            responseDTO.setMensaje(Mensaje.TASK_ACTUALIZADA);

            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("Error al actualizar la tarea: {}", e.getMessage());
            throw new TaskException(e.getMessage());
        }
    }

    @Override
    public ResponseDTO saveTask(Task task) throws TaskException {
        LOGGER.info("Tarea para guardar : {}", task);
        try {
            ResponseDTO responseDTO = new ResponseDTO();
            taskRepository.save(task);
            responseDTO.setCodigoRespuesta(HttpStatus.CREATED.value());
            responseDTO.setMensaje(Mensaje.TASK_GUARDADA);
            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("Error al guardar la tarea: {}", e.getMessage());
            throw new TaskException(e.getMessage());
        }
    }

    @Override
    public Page<Task> listTasks(Pageable pageable) throws TaskException {
        try {
            return taskRepository.findAll(pageable);
        } catch (Exception e) {
            LOGGER.error("Error al obtener lista paginada de tarea: {}", e.getMessage());
            throw new TaskException(e.getMessage());
        }
    }

    @Override
    public Task getTaskById(Integer idTask) throws TaskException {
        LOGGER.info("El id de la tarea para obtener : {}", idTask);
        try {
            return taskRepository.findById(idTask).orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error al obtener tarea: {}", e.getMessage());
            throw new TaskException(e.getMessage());
        }
    }

    @Override
    public ResponseDTO deleteTask(Integer idTask) throws TaskException {
        LOGGER.info("El id de la tarea para eliminar : {}", idTask);
        try {
            Task taskFound = getTaskById(idTask);
            if (taskFound == null) throw new TaskException(Mensaje.TASK_NO_ENCONTRADA);
            taskRepository.deleteById(idTask);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setMensaje(Mensaje.TASK_ELIMINADA);
            responseDTO.setCodigoRespuesta(HttpStatus.OK.value());
            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("Error al eliminar la tarea: {}", e.getMessage());
            throw new TaskException(e.getMessage());
        }
    }


}
