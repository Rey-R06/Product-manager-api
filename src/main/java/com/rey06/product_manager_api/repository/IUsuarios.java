package com.rey06.product_manager_api.repository;

import com.rey06.product_manager_api.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarios extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByEmail(String email);//Metodo que sirve para ver si ya existe el gmail ingresado
}
