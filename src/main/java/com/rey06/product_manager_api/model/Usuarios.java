package com.rey06.product_manager_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rey06.product_manager_api.ayudas.Rol;
import com.rey06.product_manager_api.validation.OnCreate;
import com.rey06.product_manager_api.validation.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(groups = OnCreate.class, message = "El nombre no puede estar vacío.")
    private String nombre;

    @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Correo no válido.")
    @NotBlank(groups = OnCreate.class, message = "El correo no puede estar vacío.")
    private String email;

    @NotNull(groups = OnCreate.class, message = "La contraseña no puede ser nula.")
    private String contraseña;

    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener entre 7 y 15 dígitos.")
    @NotNull(groups = OnCreate.class, message = "El teléfono no puede estar vacío.")
    private String telefono;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @PastOrPresent(message = "La fecha no puede ser futura.")
    private Date fechaRegistro;

    @NotNull(groups = OnCreate.class, message = "El rol no puede estar vacío.")
    @Enumerated(EnumType.STRING)
    @Column(length = 20) // opcionalmente para limitar el largo
    private Rol rol;

    private Boolean registrado = false; // valor por defecto

    // Usuario.java
    @ElementCollection
    @CollectionTable(name = "usuario_historial_pedidos", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "pedido_id")
    private List<Integer> historialPedidos = new ArrayList<>();


    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value = "usuario-pedidos")
    private List<Pedidos> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    @JsonBackReference(value = "usuario-producto")
    private List<Productos> productos = new ArrayList<>();

    public Usuarios() {
    }

    public Usuarios(Integer id, String nombre, String email, String contraseña, String telefono, Date fechaRegistro, Boolean registrado, Rol rol, List<Integer> historialPedidos, List<Pedidos> pedidos, List<Productos> productos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.registrado = registrado;
        this.rol = rol;
        this.historialPedidos = historialPedidos;
        this.pedidos = pedidos;
        this.productos = productos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRegistrado() {
        return registrado;
    }

    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }

    public List<Integer> getHistorialPedidos() {
        return historialPedidos;
    }

    public void setHistorialPedidos(List<Integer> historialPedidos) {
        this.historialPedidos = historialPedidos;
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

    public void setEmail (String email) {
        this.email = email;
    }

    public  String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
