package com.todolist.app.dto;

import java.sql.Timestamp;

import com.todolist.app.utils.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestTaskDTO {

    @NotBlank
    private String title;

    private String description;

    private Timestamp dueDate;

    @NotNull
    private Status status;
}