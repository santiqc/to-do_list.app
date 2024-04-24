package com.todolist.app.swagger;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;




@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "To-do List App",
                version = "1.0.0",
                description = "Aplicación que permite a los usuarios crear, ver, actualizar y eliminar tareas."
        )
)
public class SwaggerConfig {

}