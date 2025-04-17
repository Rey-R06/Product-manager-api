package com.rey06.product_manager_api.services;

import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.model.Productos;
import com.rey06.product_manager_api.repository.IProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosServices {

    @Autowired
    IProductos repository;

    public Productos crearCliente(Productos producto)throws Exception{
        try {
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
        }).orElseThrow(() -> new Exception("Admin no encontrado con ID: " + id));
    }
}
