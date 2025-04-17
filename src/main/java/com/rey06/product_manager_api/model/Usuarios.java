package com.rey06.product_manager_api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="Usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;

    @Column(name = "Email", nullable = false)
    @Email(message = "El correo electrónico no es válido.")
    @NotBlank(message = "El correo no puede estar vacío.")
    private String email;

    @Column(name = "Contraseña", nullable = false)
    private String contraseña;

   public Usuarios(){}

    public Usuarios(String nombre, String email, String contraseña){
       this.nombre = nombre;
       this.email = email;
       this.contraseña = contraseña;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
