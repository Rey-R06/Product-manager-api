package com.rey06.product_manager_api.repository;

import com.rey06.product_manager_api.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientes extends JpaRepository<Clientes, Integer> {
}
