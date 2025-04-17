package com.rey06.product_manager_api.repository;

import com.rey06.product_manager_api.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidos extends JpaRepository<Pedidos, Integer> {
}
