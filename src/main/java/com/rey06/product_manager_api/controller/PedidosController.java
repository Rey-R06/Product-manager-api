package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.services.PedidosServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Pedidos")
public class PedidosController {

    @Autowired
    private PedidosServices pedidosServices;

    @PostMapping
    public ResponseEntity<Pedidos>crearPedido(@Valid @RequestBody Pedidos pedido) throws Exception{
        Pedidos pedidoNuevo = pedidosServices.crearPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }

    @GetMapping
    public List<Pedidos>listarPrdidos() throws Exception{
        return pedidosServices.buscarTodos();
    }

    @PutMapping("/Pedidos/{id}")
    public ResponseEntity<Pedidos>actualizarPedido(@PathVariable Integer id,@Valid @RequestBody Pedidos pedido) throws Exception{
        Pedidos actualizado = pedidosServices.actualizarPedido(id, pedido);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?>eliminarPedido(@PathVariable Integer id){
        try {
            pedidosServices.eliminarPedido(id);
            return ResponseEntity.ok("Pedido eliminado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}
