package com.todolist.app.entity;

import com.todolist.app.utils.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;
    private String description ;
    private Timestamp dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;;

}
