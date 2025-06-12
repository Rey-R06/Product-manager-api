package com.rey06.product_manager_api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rey06.product_manager_api.model.Productos;
import com.rey06.product_manager_api.repository.IProductos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ProductosServices {

    @Autowired
    IProductos repository;

    public Productos crearProducto(Productos producto)throws Exception{
        try {
            if (producto.getUsuario() == null || producto.getUsuario().getId() == null) {
                throw new Exception("El producto debe estar asociado a un usuario válido.");
            }
            return this.repository.save(producto);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Buscar todos
    public List<Productos> buscarTodos()throws Exception{
        try{
            return this.repository.findAll();
        }catch (Exception error){
            throw new RuntimeException(error.getMessage());
        }
    }

    public Productos actualizarProducto(Integer id, Productos producto)throws Exception{
        return repository.findById(id).map(productoExistente -> {
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setCantidad(producto.getCantidad());
            productoExistente.setPrecio(producto.getPrecio());

            return repository.save(productoExistente);
        }).orElseThrow(() -> new Exception("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Productos actualizarParcialProducto(Integer id, Map<String, Object> camposActualizados) throws Exception {
        Productos producto = repository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado con ID: " + id));

        ObjectMapper mapper = new ObjectMapper();
        Productos productoParcial = mapper.convertValue(camposActualizados, Productos.class);

        if (camposActualizados.containsKey("nombre")) producto.setNombre(productoParcial.getNombre());
        if (camposActualizados.containsKey("descripcion")) producto.setDescripcion(productoParcial.getDescripcion());
        if (camposActualizados.containsKey("cantidad")) producto.setCantidad(productoParcial.getCantidad());
        if (camposActualizados.containsKey("precio")) producto.setPrecio(productoParcial.getPrecio());
        if (camposActualizados.containsKey("precioOriginal")) producto.setPrecioOriginal(productoParcial.getPrecioOriginal());
        if (camposActualizados.containsKey("urlImg")) producto.setUrlImg(productoParcial.getUrlImg());
        if (camposActualizados.containsKey("oferta")) producto.setOferta(productoParcial.getOferta());
        if (camposActualizados.containsKey("activo")) producto.setActivo(productoParcial.getActivo());

        return repository.save(producto);
    }



    public void eliminarProducto(Integer id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Producto no encontrado.");
        }
        repository.deleteById(id);
    }

    public void activarProducto(Integer id) {
        Productos producto = repository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(true);
        repository.save(producto);
    }
    public void desactivarProducto(Integer id) {
        Productos producto = repository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        repository.save(producto);
    }

}
