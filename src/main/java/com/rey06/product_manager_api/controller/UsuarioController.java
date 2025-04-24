package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Usuarios;
import com.rey06.product_manager_api.services.UsuarioServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @PostMapping
    public ResponseEntity<Usuarios>crearUsuario(@Valid @RequestBody Usuarios usuario)throws Exception{
        Usuarios usuarioNuevo = usuarioServices.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    @GetMapping
    public List<Usuarios>listarUsuarios()throws Exception{
        return usuarioServices.buscarTodos();
    }

    @PutMapping("/Put/{id}")
    public ResponseEntity<Usuarios>actualizarUsuario(@PathVariable Integer id,@Valid @RequestBody Usuarios usuarioActualizado)throws Exception{
        Usuarios usuarioNuevo = usuarioServices.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(usuarioNuevo);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) throws Exception {
        usuarioServices.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

}
