package com.rey06.product_manager_api.services;

import com.rey06.product_manager_api.model.Administradores;
import com.rey06.product_manager_api.model.Clientes;
import com.rey06.product_manager_api.repository.IClientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesServices {

    @Autowired
    IClientes repository;

    public Clientes crearCliente(Clientes cliente)throws Exception{
        try {
            return this.repository.save(cliente);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Buscar todos
    public List<Clientes> buscarTodos()throws Exception{
        try{
            return this.repository.findAll();
        }catch (Exception error){
            throw new RuntimeException(error.getMessage());
        }
    }

    public Clientes actualizarCliente(Integer id, Clientes datosActualizados) throws Exception{
        return repository.findById(id).map(clienteExistente -> {
            clienteExistente.setNombre(datosActualizados.getNombre());
            clienteExistente.setDireccion(datosActualizados.getDireccion());
            clienteExistente.setTelefono(datosActualizados.getTelefono());
            clienteExistente.setEmail(datosActualizados.getEmail());
            // Agrega aquí más campos si es necesario
            return repository.save(clienteExistente);
        }).orElseThrow(() -> new Exception("Admin no encontrado con ID: " + id));
    }

    public void eliminarCliente(Integer id) throws Exception {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new Exception("Cliente no encontrado con ID: " + id);
        }
    }
}
