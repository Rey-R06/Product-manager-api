package com.rey06.product_manager_api.services;


import com.rey06.product_manager_api.model.Administradores;
import com.rey06.product_manager_api.repository.IAdministradores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServices {

    @Autowired
    IAdministradores repository;

    public Administradores crearAdministrador(Administradores administrador)throws Exception{
        try {
            return this.repository.save(administrador);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    //Buscar todos
    public List<Administradores> buscarTodos()throws Exception{
        try{
            return this.repository.findAll();
        }catch (Exception error){
            throw new RuntimeException(error.getMessage());
        }
    }

    public Administradores actualizarAdministrador(Integer id, Administradores datosActualizados) throws Exception {
        return repository.findById(id).map(adminExistente -> {
            adminExistente.setNombre(datosActualizados.getNombre());
            adminExistente.setEmail(datosActualizados.getEmail());
            adminExistente.setContraseña(datosActualizados.getContraseña());
            // Agrega aquí más campos si es necesario
            return repository.save(adminExistente);
        }).orElseThrow(() -> new Exception("Admin no encontrado con ID: " + id));
    }

    public void eliminarAdministrador(Integer id) throws Exception {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new Exception("Administrador no encontrado con ID: " + id);
        }
    }
}
