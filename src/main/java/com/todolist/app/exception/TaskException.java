package com.todolist.app.exception;


import lombok.Getter;

@Getter
public class TaskException extends RuntimeException {

    private final String mensaje;

    public TaskException(String message) {
        super(message);
        this.mensaje = message;
    }

    public TaskException(Exception e) {
        super(e.getMessage(), e);

        this.mensaje = e.getMessage();

    }

    @Override
    public String toString() {
        return "TodoListExc{" +
                "mensaje='" + mensaje + '\'' +
                '}';
    }

}
