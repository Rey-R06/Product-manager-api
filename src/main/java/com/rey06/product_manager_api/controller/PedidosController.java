package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.ayudas.EstadoPedido;
import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.services.PedidosServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> camposActualizados) {
        try {
            Pedidos pedidoActualizado = pedidosServices.actualizarPedidoParcial(id, camposActualizados);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "mensaje", "No se pudo actualizar el pedido",
                    "detalle", e.getMessage()
            ));
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        try {
            String estadoStr = body.get("estado");
            EstadoPedido nuevoEstado = EstadoPedido.valueOf(estadoStr.toUpperCase());

            Pedidos actualizado = pedidosServices.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Estado inválido"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Error al actualizar estado")
            );
        }
    }




    // Eliminar un pedido (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        pedidosServices.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
