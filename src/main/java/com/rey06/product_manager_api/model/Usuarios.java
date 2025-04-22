package com.rey06.product_manager_api.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rey06.product_manager_api.ayudas.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Usuarios")
public class  Usuarios {

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

    @Column(name = "direccion", nullable = true)
    private String direccion;

    @Column(name = "telefono", nullable = true)
    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos.")
    private String telefono;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "fechaRegistro", nullable = true)
    @NotNull(message = "La fecha de registro no puede ser nula.")
    @PastOrPresent(message = "La fecha no puede ser futura.")
    private Date fechaRegistro;

    @Column(name = "rol", nullable = false)
    @NotNull(message = "no puede estar vacio.")
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value = "usuario-pedidos")
    private List<Pedidos> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value = "usuario-producto")
    private List<Productos> productos = new ArrayList<>();

    public Usuarios(){}

    public Usuarios(Integer id, String nombre, String email, String contraseña, String direccion, String telefono, Date fechaRegistro, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.rol = rol;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Pedidos> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedidos> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Productos> getProductos() {
        return productos;
    }

    public void setProductos(List<Productos> productos) {
        this.productos = productos;
    }
}
