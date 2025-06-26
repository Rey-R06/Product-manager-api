package com.rey06.product_manager_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "Productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    private String nombre;

    @Size(max = 1000, message = "La descripción no debe exceder los 1000 caracteres.")
    private String descripcion;

    @Positive(message = "El precio debe ser mayor que cero.")
    private Float precio;

    @NotNull(message = "El precio original no puede ser nulo.")
    @Positive(message = "El precio original debe ser mayor que cero.")
    private Float precioOriginal;

    @Column(nullable = false)
    private Boolean oferta;

    @NotNull(message = "El stock no puede ser nulo.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer cantidad;

    @NotBlank(message = "La categoría no puede estar vacía.")
    private String categoria;


    @Size(max = 500, message = "La URL de la imagen no debe exceder 500 caracteres.")
    private String urlImg;

    private boolean activo = true;

    @OneToMany(mappedBy = "producto")
    private List<ItemPedido> itemsPedido;

    @ManyToOne
    @JoinColumn(name = "creado_por_usuario", nullable = true)
    @JsonBackReference(value = "usuario-producto")
    private Usuarios usuario;

    public Productos() {
    }

    public Productos(Integer id, String nombre, String descripcion, Float precio, Float precioOriginal, Boolean oferta, Integer cantidad, String categoria, String urlImg, boolean activo, Usuarios usuario, List<ItemPedido> itemsPedido) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioOriginal = precioOriginal;
        this.oferta = oferta;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.urlImg = urlImg;
        this.activo = activo;
        this.usuario = usuario;
        this.itemsPedido = itemsPedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal( Float precioOriginal) {
        this.precioOriginal = precioOriginal;
    }

    public Boolean getOferta() {
        return oferta;
    }

    public void setOferta(Boolean oferta) {
        this.oferta = oferta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isActivo() {
        return activo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }



    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
