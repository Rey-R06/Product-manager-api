package com.rey06.product_manager_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @NotNull(message = "El cliente no puede ser nulo.")
    @ManyToOne
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id")
    @JsonBackReference//la que tenga la many lleva jsonback
    private Clientes cliente;

    @NotEmpty(message = "Debe haber al menos un producto en el pedido.")
    @ManyToMany
    @JoinTable(
            name = "pedido_producto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    @JsonManagedReference
    private List<Productos> productos;


    @Column(name = "totalCompra", nullable = false)
    private Float totalCompra;

    public Pedidos(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
