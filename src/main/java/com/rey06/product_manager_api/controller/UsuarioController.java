package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Usuarios;
import com.rey06.product_manager_api.services.UsuarioServices;
import com.rey06.product_manager_api.validation.OnCreate;
import com.rey06.product_manager_api.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @PostMapping
    public ResponseEntity<Usuarios>crearUsuario(@Validated({OnCreate.class}) @RequestBody Usuarios usuario)throws Exception{
        Usuarios usuarioNuevo = usuarioServices.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    @GetMapping
    public List<Usuarios>listarUsuarios()throws Exception{
        return usuarioServices.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualizarUsuario(
            @NotNull(message = "El ID no puede ser nulo.")
            @Positive(message = "El ID debe ser positivo.")
            @PathVariable Integer id,
            @Validated({OnUpdate.class}) @RequestBody Usuarios usuarioActualizado
    ) throws Exception {
        Usuarios usuarioNuevo = usuarioServices.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(usuarioNuevo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuarios> actualizarParcialUsuario(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> camposActualizados) throws Exception {
        Usuarios usuarioActualizado = usuarioServices.actualizarParcial(id, camposActualizados);
        return ResponseEntity.ok(usuarioActualizado);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(
            @NotNull(message = "El ID no puede ser nulo.")
            @Positive(message = "El ID debe ser positivo.")
            @PathVariable Integer id) throws Exception {

        usuarioServices.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

}
