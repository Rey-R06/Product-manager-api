package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.services.PedidosServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
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

    @PutMapping("/{id}")
    public ResponseEntity<Pedidos>actualizarPedido(@PathVariable Integer id,@Valid @RequestBody Pedidos pedido) throws Exception{
        Pedidos actualizado = pedidosServices.actualizarPedido(id, pedido);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pedidos> actualizarParcialPedido(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> camposActualizados) {
        try {
            Pedidos pedidoActualizado = pedidosServices.actualizarParcialPedido(id, camposActualizados);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminarPedido(@PathVariable Integer id){
        try {
            pedidosServices.eliminarPedido(id);//hacer un push antes de seguir
            return ResponseEntity.ok("Pedido eliminado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}
