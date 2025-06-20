package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.services.PedidosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidosController {

    @Autowired
    private PedidosServices pedidosServices;

    // Crear un pedido (POST)
    @PostMapping
    public ResponseEntity<Pedidos> crearPedido(@RequestBody Pedidos pedido) throws Exception {
        return ResponseEntity.ok(pedidosServices.crearPedido(pedido));
    }

    // Obtener todos los pedidos (GET)
    @GetMapping
    public ResponseEntity<List<Pedidos>> obtenerTodos() throws Exception {
        return ResponseEntity.ok(pedidosServices.buscarTodos());
    }

    // Actualizar pedido completo (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Pedidos> actualizarPedido(
            @PathVariable Integer id,
            @RequestBody Pedidos pedidoActualizado
    ) throws Exception{
        Pedidos pedido = pedidosServices.actualizarPedido(id, pedidoActualizado);
        return ResponseEntity.ok(pedido);
    }


    // Actualizar parcialmente un pedido (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Pedidos> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> camposActualizados) throws Exception {
        return ResponseEntity.ok(pedidosServices.actualizarPedidoParcial(id, camposActualizados));
    }

    // Eliminar un pedido (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        pedidosServices.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
