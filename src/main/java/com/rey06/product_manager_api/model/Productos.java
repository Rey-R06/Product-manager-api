package com.rey06.product_manager_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name="Productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    private String nombre;

    @Column(name = "precio", nullable = false)
    @NotNull(message = "El precio no puede ser nulo.")
    @Positive(message = "El precio debe ser mayor que cero.")
    private Float precio;

    @Column(name = "cantidad", nullable = false)
    @NotNull(message = "El stock no puede ser nulo.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer cantidad;

    // En Producto (opcional, si quieres bidireccional)
    @ManyToMany(mappedBy = "productos")
    @JsonIgnore
    private List<Pedidos> pedidos;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference(value = "usuario-producto")
    private Usuarios usuario;

    public Productos() {}

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

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Usuarios getUsuario() {return usuario;}

    public void setUsuario(Usuarios usuario) {this.usuario = usuario;}

    public List<Pedidos> getPedidos() {return pedidos;}

    public void setPedidos(List<Pedidos> pedidos) {this.pedidos = pedidos;}
}
