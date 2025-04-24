package com.rey06.product_manager_api.controller;

import com.rey06.product_manager_api.model.Productos;
import com.rey06.product_manager_api.services.ProductosServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Producto")
public class ProductosController {

    @Autowired
    private ProductosServices productosServices;

    @PostMapping
    public ResponseEntity<Productos>crearProducto(@Valid @RequestBody Productos producto)throws Exception{
        Productos productoNuevo = productosServices.crearCliente(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping
    public List<Productos>listarProductos()throws Exception{
        return productosServices.buscarTodos();
    }

    @PutMapping("/Productos/{id}")
    public ResponseEntity<Productos>actualizarProducto(@PathVariable Integer id, @Valid @RequestBody Productos productoActualizado) throws Exception{
        Productos actualizado = productosServices.actualizarProducto(id, productoActualizado);
        return ResponseEntity.ok(actualizado);
    }
}
