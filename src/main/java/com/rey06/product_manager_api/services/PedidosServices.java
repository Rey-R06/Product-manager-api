package com.rey06.product_manager_api.services;

import com.rey06.product_manager_api.model.Pedidos;
import com.rey06.product_manager_api.model.Productos;
import com.rey06.product_manager_api.model.Usuarios;
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

    @Transactional//Esto asegura que si algo falla, todo se revierte para no alterar el stock
    public Pedidos crearPedido(Pedidos pedido) throws Exception {
        try {
            // Inicializamos el total de la compra en 0
            final Float[] total = {0f};


            //validamos si existe el usuario
           validarExistenciaUsuario(pedido.getUsuario());

            // Si pasó todas las validaciones, continuamos con el usuario válido

            // Contamos productos como en crearPedido
            Map<Integer, Integer> contadorProductos = contarProductos(pedido.getProductos());

            //Verificamos que el producto tenga suficiente stock y crea el producto con sus datos finales
            List<Productos> productosFinales = verificarStockYConstruirProductosFinales(contadorProductos, total);


            // Asociamos la lista validada de productos al pedido
            pedido.setProductos(productosFinales);

            // Establecemos el total calculado
            pedido.setTotalCompra(total[0]);

            // Establecemos la fecha actual como fecha del pedido
            pedido.setFecha(new Date());

            // Guardamos el pedido en la base de datos y lo retornamos
            return repository.save(pedido);

        } catch (Exception e) {
            // Si ocurre cualquier error, lo envolvemos y relanzamos
            throw new RuntimeException("Error al crear el pedido: " + e.getMessage());
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

    @Transactional
    public Pedidos actualizarPedido(Integer id, Pedidos pedidoActualizado) throws Exception {
        // Validamos que el usuario exista
        validarExistenciaUsuario(pedidoActualizado.getUsuario());

        // Buscamos el pedido existente por ID
        Pedidos pedidoExistente = repository.findById(id)
                .orElseThrow(() -> new Exception("Pedido no encontrado con ID: " + id));

        // Restauramos el stock anterior
        Map<Integer, Integer> contadorStockAnterior = contarProductos(pedidoExistente.getProductos());
        for (Map.Entry<Integer, Integer> entry : contadorStockAnterior.entrySet()) {
            Integer productoId = entry.getKey();
            Integer cantidad = entry.getValue();

            Productos producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + productoId + " no encontrado al restaurar stock (actualizar)"));

            producto.setCantidad(producto.getCantidad() + cantidad);
            productoRepository.save(producto);
        }

        //Contamos los nuevos productos
        Map<Integer, Integer> contadorProductos = contarProductos(pedidoActualizado.getProductos());

        //Verificamos stock y construimos lista de productos final
        final Float[] total = {0f};
        List<Productos> productosFinales = verificarStockYConstruirProductosFinales(contadorProductos, total);

        //Actualizamos los campos del pedido
        pedidoExistente.setProductos(productosFinales);
        pedidoExistente.setTotalCompra(total[0]);
        pedidoExistente.setUsuario(pedidoActualizado.getUsuario());
        pedidoExistente.setFecha(new Date());

        return repository.save(pedidoExistente);
    }


    public void eliminarPedido(Integer pedidoId) {
        Pedidos pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido con ID " + pedidoId + " no encontrado"));

        // Restaurar el stock de productos
        Map<Integer, Integer> contadorProductos = new HashMap<>();
        for (Productos p : pedido.getProductos()) {
            contadorProductos.put(p.getId(), contadorProductos.getOrDefault(p.getId(), 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : contadorProductos.entrySet()) {
            Integer productoId = entry.getKey();
            Integer cantidad = entry.getValue();

            Productos producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + productoId + " no encontrado al restaurar stock"));

            // Restaurar cantidad al stock
            producto.setCantidad(producto.getCantidad() + cantidad);
            productoRepository.save(producto);
        }

        // Eliminar el pedido
        repository.deleteById(pedidoId);
    }

    private Map<Integer, Integer> contarProductos(List<Productos> productos) {
        Map<Integer, Integer> contador = new HashMap<>();
        for (Productos p : productos) {
            contador.put(p.getId(), contador.getOrDefault(p.getId(), 0) + 1);
        }
        return contador;
    }

    private void validarExistenciaUsuario(Usuarios usuario) {
        // Validación combinada en una sola condición para mejor legibilidad
        if (usuario.getId() == null || !usuarioRepository.existsById(usuario.getId())) { //1 ¿El usuario tiene ID válido? 2 ¿Existe en BD?
            // Lanzamos excepción con información detallada
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,            // Código HTTP 404
                    "Usuario no válido o no encontrado. " + // Mensaje claro
                            (usuario == null ? "No asociado al pedido" :
                                    "ID: " + usuario.getId())        // Info contextual
            );
        }
    }

    private List<Productos> verificarStockYConstruirProductosFinales(Map<Integer, Integer> contadorProductos, Float[] total) {
        // Esta será la lista final de productos válidos, ya verificados y actualizados desde la base de datos
        List<Productos> productosFinales = new ArrayList<>();

        // Iteramos por cada producto distinto en el mapa
        for (Map.Entry<Integer, Integer> entry : contadorProductos.entrySet()) {
            Integer productoId = entry.getKey();// ID del producto
            Integer cantidadSolicitada = entry.getValue();// Cuántas unidades se solicitaron

            // Buscamos el producto en la base de datos
            Productos producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + productoId + " no encontrado"));

            // Verificamos si hay stock suficiente
            if (producto.getCantidad() < cantidadSolicitada) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Descontamos la cantidad solicitada del stock
            producto.setCantidad(producto.getCantidad() - cantidadSolicitada);
            // Guardamos el nuevo estado del producto (con el stock actualizado)
            productoRepository.save(producto);

            // Sumamos al total el precio del producto * cantidad solicitada
            total[0] += producto.getPrecio() * cantidadSolicitada;

            // Agregamos el producto a la lista final tantas veces como unidades se pidieron
            for (int i = 0; i < cantidadSolicitada; i++) {
                productosFinales.add(producto);
            }
        }

        return productosFinales;
    }

};