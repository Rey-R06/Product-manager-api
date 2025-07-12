package com.rey06.product_manager_api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rey06.product_manager_api.ayudas.EstadoPedido;
import com.rey06.product_manager_api.ayudas.MetodoDePago;
import com.rey06.product_manager_api.model.*;
import com.rey06.product_manager_api.repository.IItemPedido;
import com.rey06.product_manager_api.repository.IPedidos;
import com.rey06.product_manager_api.repository.IProductos;
import com.rey06.product_manager_api.repository.IUsuarios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PedidosServices {

    @Autowired
    IPedidos repository;

    @Autowired
    private IProductos productoRepository;

    @Autowired
    private IUsuarios usuarioRepository;

    @Autowired
    private IItemPedido itemPedidoRepository;

    @Transactional
    public Pedidos crearPedido(Pedidos pedido) {
        // Validar usuario (si viene)
        if (pedido.getUsuario() != null) {
            validarExistenciaUsuario(pedido.getUsuario());
        }

        float total = 0f;

        // Procesar itemsPedido
        for (ItemPedido item : pedido.getItemsPedido()) {
            if (item.getProducto() == null || item.getProducto().getId() == null) {
                throw new IllegalArgumentException("Cada itemPedido debe tener un producto con ID.");
            }

            Productos producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + item.getProducto().getId()));

            // Verificar stock
            if (producto.getCantidad() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Descontar stock
            producto.setCantidad(producto.getCantidad() - item.getCantidad());
            productoRepository.save(producto);

            // Calcular subtotal
            float subtotal = producto.getPrecio() * item.getCantidad();
            total += subtotal;

            // Enlazar itemPedido al pedido (para persistencia)
            item.setPedido(pedido);
            item.setProducto(producto);

            // ✅ Establecer precio unitario explícitamente
            item.setPrecioUnitario(producto.getPrecio());
        }

        pedido.setTotalCompra(total);

        // Fecha actual
        if (pedido.getFechaDelPedido() == null) {
            pedido.setFechaDelPedido(new Date());
        }

        return repository.save(pedido);
    }


    public List<Pedidos> buscarTodos() {
        return repository.findAll();
    }


    @Transactional
    public Pedidos actualizarPedido(Integer id, Pedidos pedidoActualizado) throws Exception {
        // Validar usuario
        if (pedidoActualizado.getUsuario() != null) {
            validarExistenciaUsuario(pedidoActualizado.getUsuario());
        }

        Pedidos pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new Exception("Pedido no encontrado con ID: " + id));

        // Restaurar stock anterior
        for (ItemPedido item : pedidoExistente.getItemsPedido()) {
            Productos producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + item.getProducto().getId() + " no encontrado"));

            producto.setCantidad(producto.getCantidad() + item.getCantidad());
            productoRepository.save(producto);
        }

        // Eliminar items anteriores
        itemPedidoRepository.deleteAll(pedidoExistente.getItemsPedido());
        pedidoExistente.getItemsPedido().clear();

        float total = 0f;

        // Procesar nuevos itemsPedido
        for (ItemPedido item : pedidoActualizado.getItemsPedido()) {
            Productos producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + item.getProducto().getId()));

            if (producto.getCantidad() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setCantidad(producto.getCantidad() - item.getCantidad());
            productoRepository.save(producto);

            total += producto.getPrecio() * item.getCantidad();

            item.setPedido(pedidoExistente);
            item.setProducto(producto);
            pedidoExistente.getItemsPedido().add(item);
        }

        pedidoExistente.setTotalCompra(total);
        pedidoExistente.setFechaDelPedido(new Date());
        pedidoExistente.setEstado(pedidoActualizado.getEstado());
        pedidoExistente.setMetodoDePago(pedidoActualizado.getMetodoDePago());
        pedidoExistente.setDireccionEntrega(pedidoActualizado.getDireccionEntrega());
        pedidoExistente.setNombreDelPedido(pedidoActualizado.getNombreDelPedido());
        pedidoExistente.setEmailDelPedido(pedidoActualizado.getEmailDelPedido());
        pedidoExistente.setTelefonoDelPedido(pedidoActualizado.getTelefonoDelPedido());
        pedidoExistente.setUsuario(pedidoActualizado.getUsuario());
        pedidoExistente.setRegistrado(pedidoActualizado.isRegistrado());

        return repository.save(pedidoExistente);
    }

    public Pedidos actualizarEstado(Integer id, EstadoPedido nuevoEstado) throws Exception {
        Pedidos pedido = repository.findById(id)
                .orElseThrow(() -> new Exception("Pedido no encontrado"));

        pedido.setEstado(nuevoEstado);
        return repository.save(pedido);
    }



    @Transactional
    public Pedidos actualizarPedidoParcial(Integer id, Map<String, Object> updates) throws Exception {
        Pedidos pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new Exception("Pedido no encontrado con ID: " + id));

        ObjectMapper objectMapper = new ObjectMapper();

        // Si se actualiza "estado"
        if (updates.containsKey("estado")) {
            EstadoPedido nuevoEstado = objectMapper.convertValue(updates.get("estado"), EstadoPedido.class);
            pedidoExistente.setEstado(nuevoEstado);
        }

        // Si se actualiza "metodoDePago"
        if (updates.containsKey("metodoDePago")) {
            MetodoDePago nuevoMetodo = objectMapper.convertValue(updates.get("metodoDePago"), MetodoDePago.class);
            pedidoExistente.setMetodoDePago(nuevoMetodo);
        }

        // Si se actualiza "direccionEntrega"
        if (updates.containsKey("direccionEntrega")) {
            pedidoExistente.setDireccionEntrega((String) updates.get("direccionEntrega"));
        }

        // Si se actualiza "nombreDelPedido"
        if (updates.containsKey("nombreDelPedido")) {
            pedidoExistente.setNombreDelPedido((String) updates.get("nombreDelPedido"));
        }

        // Si se actualiza "emailDelPedido"
        if (updates.containsKey("emailDelPedido")) {
            pedidoExistente.setEmailDelPedido((String) updates.get("emailDelPedido"));
        }

        // Si se actualiza "telefonoDelPedido"
        if (updates.containsKey("telefonoDelPedido")) {
            pedidoExistente.setTelefonoDelPedido((String) updates.get("telefonoDelPedido"));
        }

        // Si se actualiza el "usuario" (usuario_id)
        if (updates.containsKey("usuario")) {
            Map<String, Object> usuarioMap = objectMapper.convertValue(updates.get("usuario"), new TypeReference<Map<String, Object>>() {});
            Integer usuarioId = (Integer) usuarioMap.get("id");
            if (usuarioId != null && usuarioRepository.existsById(usuarioId)) {
                Usuarios usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario con ID: " + usuarioId + " no encontrado"));
                pedidoExistente.setUsuario(usuario);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con ID " + usuarioId + " no encontrado");
            }
        }

        // Si se actualiza "registrado"
        if (updates.containsKey("registrado")) {
            boolean nuevoValor = (Boolean) updates.get("registrado");
            pedidoExistente.setRegistrado(nuevoValor); // ✅
        }

        return repository.save(pedidoExistente);
    }


    @Transactional
    public void eliminarPedido(Integer pedidoId) {
        Pedidos pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + pedidoId + " no encontrado"));

        // Restaurar stock
        for (ItemPedido item : pedido.getItemsPedido()) {
            Productos producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + item.getProducto().getId() + " no encontrado"));

            producto.setCantidad(producto.getCantidad() + item.getCantidad());
            productoRepository.save(producto);
        }

        // Eliminar items
        itemPedidoRepository.deleteAll(pedido.getItemsPedido());

        // Eliminar pedido
        repository.deleteById(pedidoId);
    }

    private void validarExistenciaUsuario(Usuarios usuario) {
        if (usuario == null || usuario.getId() == null || !usuarioRepository.existsById(usuario.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no válido o no encontrado. " + (usuario == null ? "No asociado al pedido" : "ID: " + usuario.getId())
            );
        }
    }

}
