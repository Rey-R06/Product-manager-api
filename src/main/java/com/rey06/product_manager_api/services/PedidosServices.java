package com.rey06.product_manager_api.services;

import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.model.Productos;
import com.rey06.product_manager_api.repository.IPedidos;
import com.rey06.product_manager_api.repository.IProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidosServices {

    @Autowired
    IPedidos repository;

    @Autowired
    private IProductos productoRepository;


    public Pedidos crearPedido(Pedidos pedido)throws Exception{
        try {
            // Calcular el total de la compra basado en los productos
            Float total = 0f;
            // lista nueva con productos sacados de la base de datos (completos)
            List<Productos> productosFinales = new ArrayList<>();

            if (pedido.getProductos() != null) {
                for (Productos producto : pedido.getProductos()) {
                    // Buscamos el producto por ID y lo validamos
                    Productos productoExistente = productoRepository.findById(producto.getId())
                            .orElseThrow(() -> new RuntimeException("Producto con ID " + producto.getId() + " no encontrado"));
                    // Sumamos el precio al total
                    total += productoExistente.getPrecio();
                    // Agregamos el producto real a la lista
                    productosFinales.add(productoExistente);
                }
            }

           // Asignamos la lista final y válida de productos al pedido
            pedido.setProductos(productosFinales);
            pedido.setTotalCompra(total);
            return repository.save(pedido);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Buscar todos
    public List<Pedidos> buscarTodos()throws Exception{
        try{
            return this.repository.findAll();
        }catch (Exception error){
            throw new RuntimeException(error.getMessage());
        }
    }

    public Pedidos actualizarPedido(Integer id, Pedidos pedidoActualizado)throws Exception{
        // Usamos un arreglo de Float como "truco" para modificar su valor dentro de la lambda
        final Float[] total = {0f};
        // Lista donde guardaremos los productos completos (desde la base de datos)
        List<Productos> productosFinales = new ArrayList<>();

        // Buscamos el pedido por ID, si existe lo actualizamos
        return repository.findById(id).map(pedidoExistente -> {
            // Buscamos el pedido por ID, si existe lo actualizamos
            pedidoExistente.setFecha(pedidoExistente.getFecha());

            // Recorremos los productos que vienen en el pedido actualizado
            for (Productos producto : pedidoActualizado.getProductos()) {
                // Buscamos cada producto por ID en la base de datos para obtener sus datos completos
                Productos productoExistente = productoRepository.findById(producto.getId())
                        .orElseThrow(() -> new RuntimeException("Producto con ID " + producto.getId() + " no encontrado"));

                // Sumamos el precio del producto al total del pedido
                total[0] += productoExistente.getPrecio();

                // Añadimos el producto completo (con nombre, precio, etc.) a la lista final
                productosFinales.add(productoExistente);
            }
            // Asignamos la lista de productos completos al pedido existente
            pedidoExistente.setProductos(productosFinales);

            // Asignamos el total calculado al pedido
            pedidoExistente.setTotalCompra(total[0]);

            // Guardamos el pedido actualizado y lo retornamos
            return repository.save(pedidoExistente);
        }).orElseThrow(() -> new Exception("Admin no encontrado con ID: " + id));// Si no se encuentra el pedido, lanzamos una excepción
    }
}
