package com.rey06.product_manager_api.repository;

import com.rey06.product_manager_api.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductos extends JpaRepository<Productos, Integer> {
}
