package com.todolist.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2116568911643824350L;

    private String mensaje;
    private int codigoRespuesta;
    private transient Object data;


}
