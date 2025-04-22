package com.rey06.product_manager_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Pedidos")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha")
    @NotNull(message = "La fecha del pedido no puede ser nula.")
    @PastOrPresent(message = "La fecha del pedido no puede ser futura.")
    private Date fechaDelPedido;

    @Column(name = "total_compra", nullable = false)
    private Float totalCompra;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference(value = "usuario-pedidos")
    private Usuarios usuario;

    @NotEmpty(message = "Debe haber al menos un producto en el pedido.")
    @ManyToMany
    @JoinTable(
            name = "pedido_producto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Productos> productos;


    public Pedidos(){}

    public Pedidos(Integer id, Date fechaDelPedido, Float totalCompra, Usuarios usuario) {
        this.id = id;
        this.fechaDelPedido = fechaDelPedido;
        this.totalCompra = totalCompra;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {this.id = id;}

    public Date getFecha() {
        return fechaDelPedido;
    }

    public void setFecha(Date fechaDelPedido) {
        this.fechaDelPedido = fechaDelPedido;
    }

    public Float getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Float totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Usuarios getUsuario() {return usuario;}

    public void setUsuario(Usuarios usuario) {this.usuario = usuario;}

    public List<Productos> getProductos() {return productos;}

    public void setProductos( List<Productos> productos) {this.productos = productos;}
}
