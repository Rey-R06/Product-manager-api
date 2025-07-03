package com.rey06.product_manager_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rey06.product_manager_api.ayudas.EstadoPedido;
import com.rey06.product_manager_api.ayudas.MetodoDePago;
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

    @PastOrPresent(message = "La fecha del pedido no puede ser futura.")
    private Date fechaDelPedido;

    @Positive(message = "El total debe ser mayor a 0.")
    private Float totalCompra;

    @NotNull(message = "El estado del pedido no puede ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    // Relación con usuario registrado (opcional)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"pedidos"})
    private Usuarios usuario;

    // Datos básicos para pedidos de invitados
    private String nombreDelPedido;
    private String emailDelPedido;
    private String telefonoDelPedido;

    @Enumerated(EnumType.STRING)
    private MetodoDePago metodoDePago;

    @Column(nullable = false)
    private String direccionEntrega;

    @NotEmpty(message = "Debe haber al menos un producto en el pedido.")
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itemsPedido;


    public Pedidos() {
    }

    public Pedidos(Integer id, Date fechaDelPedido, Float totalCompra, EstadoPedido estado, Usuarios usuario, String nombreDelPedido, String emailDelPedido, String telefonoDelPedido, MetodoDePago metodoDePago, String direccionEntrega, List<ItemPedido> itemsPedido) {
        this.id = id;
        this.fechaDelPedido = fechaDelPedido;
        this.totalCompra = totalCompra;
        this.estado = estado;
        this.usuario = usuario;
        this.nombreDelPedido = nombreDelPedido;
        this.emailDelPedido = emailDelPedido;
        this.telefonoDelPedido = telefonoDelPedido;
        this.metodoDePago = metodoDePago;
        this.direccionEntrega = direccionEntrega;
        this.itemsPedido = itemsPedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaDelPedido() {
        return fechaDelPedido;
    }

    public void setFechaDelPedido(Date fechaDelPedido) {
        this.fechaDelPedido = fechaDelPedido;
    }

    public Float getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Float totalCompra) {
        this.totalCompra = totalCompra;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public MetodoDePago getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(MetodoDePago metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getNombreDelPedido() {
        return nombreDelPedido;
    }

    public void setNombreDelPedido(String nombreDelPedido) {
        this.nombreDelPedido = nombreDelPedido;
    }

    public String getEmailDelPedido() {
        return emailDelPedido;
    }

    public void setEmailDelPedido(String emailDelPedido) {
        this.emailDelPedido = emailDelPedido;
    }

    public String getTelefonoDelPedido() {
        return telefonoDelPedido;
    }

    public void setTelefonoDelPedido(String telefonoDelPedido) {
        this.telefonoDelPedido = telefonoDelPedido;
    }

    public List<ItemPedido> getItemsPedido() {
        return itemsPedido;
    }

    public void setItemsPedido(List<ItemPedido> itemsPedido) {
        this.itemsPedido = itemsPedido;
    }
}
