package com.rey06.product_manager_api.repository;

import com.rey06.product_manager_api.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProductos extends JpaRepository<Productos, Integer> {
    List<Productos> findByActivoTrue();// Solo productos activos
    List<Productos> findByActivoFalse();// Solo productos inactivos
    List<Productos> findByUsuarioId(Integer usuarioId);
    List<Productos> findByOfertaTrue(); // Productos en oferta
}
