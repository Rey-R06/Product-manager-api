package com.rey06.product_manager_api.services;

import com.rey06.product_manager_api.model.Usuarios;
import com.rey06.product_manager_api.repository.IUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsuarioServices {

    @Autowired
    IUsuarios repository;

    public Usuarios crearUsuario(Usuarios usuario)throws Exception{
        try {
            //findByEmail = Metodo que sirve para ver si ya existe el gmail ingresado
            if (repository.findByEmail(usuario.getEmail()).isPresent()) {
                throw new RuntimeException("El correo ya está registrado.");
            }
            usuario.setFechaRegistro(new Date());
            return repository.save(usuario);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Buscar todos
    public List<Usuarios> buscarTodos()throws Exception{
        try{
            return this.repository.findAll();
        }catch (Exception error){
            throw new RuntimeException(error.getMessage());
        }
    }

    public Usuarios actualizarUsuario(Integer id, Usuarios usuarioActualizado)throws Exception{
        return repository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setContraseña(usuarioActualizado.getContraseña());
            usuarioExistente.setEmail(usuarioActualizado.getEmail());

            return repository.save(usuarioExistente);
        }).orElseThrow(() -> new Exception("Admin no encontrado con ID: " + id));
    }

    public void eliminarUsuario(Integer id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Usuario no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

}
