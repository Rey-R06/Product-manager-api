package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Administradores;
import com.rey06.product_manager_api.services.AdministradorServices;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Administrador")
public class AdminController {

    @Autowired
    private AdministradorServices administradorServices;

    @PostMapping
    public ResponseEntity<Administradores>crearAdmin(@Valid @RequestBody Administradores administrador)throws Exception{
        Administradores adminNuevo = administradorServices.crearAdministrador(administrador);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminNuevo);
    }

    @GetMapping
    public List<Administradores> listAdministradores()throws Exception{
        return administradorServices.buscarTodos();
    }

    @PutMapping("/administradores/{id}")
    public ResponseEntity<Administradores> actualizarAdministrador(@PathVariable Integer id, @Valid @RequestBody Administradores adminDatos) throws Exception {
        Administradores actualizado = administradorServices.actualizarAdministrador(id, adminDatos);
        return ResponseEntity.ok(actualizado);
    }
/*
    @DeleteMapping("/administradores/{id}")
    public ResponseEntity<?> eliminarAdministrador(@PathVariable Integer id) throws Exception {
        administradorServices.eliminarAdministrador(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
 */
}
