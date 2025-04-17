package com.rey06.product_manager_api.model;

import com.rey06.product_manager_api.ayudas.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Administradores extends Usuarios {

    @Column(name = "rol", nullable = false)
    @NotBlank(message = "El rol no puede estar vacío.")
    private Rol rol;

    public Administradores(){}

    public Administradores(String nombre, String email, String contraseña, Rol rol) {
        super(nombre, email, contraseña);
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
