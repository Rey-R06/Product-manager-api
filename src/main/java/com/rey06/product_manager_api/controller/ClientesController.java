package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Clientes;
import com.rey06.product_manager_api.services.ClientesServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Clientes")
public class ClientesController {

    @Autowired
    private ClientesServices clientesServices;

    @PostMapping
    public ResponseEntity<Clientes>crearCliente(@Valid @RequestBody Clientes cliente)throws Exception{
        Clientes clienteNuevo = clientesServices.crearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNuevo);
    }

    @GetMapping
    public List<Clientes> listarClientes()throws Exception{
        return clientesServices.buscarTodos();
    }

    @PutMapping("/Cliente/{id}")
    public ResponseEntity<Clientes>actualizarCliente(@PathVariable Integer id,@Valid @RequestBody Clientes cliente)throws Exception{
        Clientes actualizado = clientesServices.actualizarCliente(id, cliente);
        return ResponseEntity.ok(actualizado);
    }
/*
    @DeleteMapping("/Cliente/{id}")
    public ResponseEntity<?>eliminarCliente(@PathVariable Integer id)throws Exception{
        clientesServices.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
*/
}
