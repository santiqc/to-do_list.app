package com.todolist.app.exception;


public class TaskException extends RuntimeException {

    private final String mensaje;

    public TaskException(String message) {
        super(message);
        this.mensaje = message;
    }

    @Override
    public String toString() {
        return "TodoListExc{" +
                "mensaje='" + mensaje + '\'' +
                '}';
    }

    public String getMensaje() {
        return mensaje;
    }
}
