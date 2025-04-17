package com.rey06.product_manager_api.services;

import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.repository.IPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosServices {

    @Autowired
    IPedidos repository;

    public Pedidos crearPedido(Pedidos pedido)throws Exception{
        try {
            return this.repository.save(pedido);
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
        return repository.findById(id).map(pedidoExistente -> {
            pedidoExistente.setFecha(pedidoExistente.getFecha());
            pedidoExistente.setTotalCompra(pedidoExistente.getTotalCompra());

            return repository.save(pedidoExistente);
        }).orElseThrow(() -> new Exception("Admin no encontrado con ID: " + id));
    }
}
