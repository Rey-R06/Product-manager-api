package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Productos;
import com.rey06.product_manager_api.services.ProductosServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosServices productosServices;

    @PostMapping
    public ResponseEntity<Productos>crearProducto(@Valid @RequestBody Productos producto)throws Exception{
        Productos productoNuevo = productosServices.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping
    public List<Productos>listarProductos()throws Exception{
        return productosServices.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Productos>actualizarProducto(@PathVariable Integer id, @Valid @RequestBody Productos productoActualizado) throws Exception{
        Productos actualizado = productosServices.actualizarProducto(id, productoActualizado);
        return ResponseEntity.ok(actualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Productos> actualizarParcialProducto(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> camposActualizados) {
        try {
            Productos producto = productosServices.actualizarParcialProducto(id, camposActualizados);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<String> activarProducto(@PathVariable Integer id) {
        try {
            productosServices.activarProducto(id);
            return ResponseEntity.ok("Producto activado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<String> desactivarProducto(@PathVariable Integer id) {
        try {
            productosServices.desactivarProducto(id);
            return ResponseEntity.ok("Producto desactivado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}