package com.rey06.product_manager_api.repository;

import com.rey06.product_manager_api.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarios extends JpaRepository<Usuarios, Integer> {
}
