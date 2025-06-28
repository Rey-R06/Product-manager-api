package com.rey06.product_manager_api.services;

import com.rey06.product_manager_api.ayudas.Rol;
import com.rey06.product_manager_api.model.Usuarios;
import com.rey06.product_manager_api.repository.IUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    //Buscar 1
    public Usuarios obtenerUsuarioPorId(Integer id) {
        return repository.findById(id).orElse(null);
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

            if (usuarioActualizado.getNombre() != null){
                usuarioExistente.setNombre(usuarioActualizado.getNombre());
            }
            if (usuarioActualizado.getContraseña() != null){
                usuarioExistente.setContraseña(usuarioActualizado.getContraseña());
            }
            if (usuarioActualizado.getEmail() != null){
                usuarioExistente.setEmail(usuarioActualizado.getEmail());
            }

            return repository.save(usuarioExistente);
        }).orElseThrow(() -> new Exception("Usuario no encontrado con ID: " + id));
    }

    public Usuarios actualizarParcial(Integer id, Map<String, Object> camposActualizados) {
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        camposActualizados.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Usuarios.class, key);
            if (field != null) {
                field.setAccessible(true);

                // Si el campo es "rol", convertir manualmente el String a Enum
                if (key.equals("rol")) {
                    try {
                        value = Rol.valueOf(value.toString());
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Valor inválido para el rol: " + value);
                    }
                }

                ReflectionUtils.setField(field, usuario, value);
            }
        });

        return repository.save(usuario);
    }

    // UsuarioService.java
    public Usuarios agregarPedidoAlHistorial(Integer usuarioId, Integer pedidoId) throws Exception {
        Usuarios usuario = repository.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!usuario.getHistorialPedidos().contains(pedidoId)) {
            usuario.getHistorialPedidos().add(pedidoId);
        }

        return repository.save(usuario);
    }



    public void eliminarUsuario(Integer id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Usuario no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

}
