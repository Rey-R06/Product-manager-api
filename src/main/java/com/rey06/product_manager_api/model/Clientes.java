package com.rey06.product_manager_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;


@Entity
public class Clientes extends Usuarios{

    @Column(name = "direccion", nullable = false)
    @NotBlank(message = "La dirección no puede estar vacía.")
    private String direccion;

    @Column(name = "telefono", nullable = false)
    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos.")
    private String telefono;

    @Column(name = "fechaRegistro", nullable = false)
    @NotNull(message = "La fecha de registro no puede ser nula.")
    @PastOrPresent(message = "La fecha no puede ser futura.")
    private Date fechaRegistro;

    //creando relacion(1 a muchos) con asistencia
    @OneToMany(mappedBy = "cliente")//=Como es conocido esta tabla en la tabla pedidos
    @JsonManagedReference//la que tenga la one lleva jsonmanage
    private List<Pedidos> pedido;

    public Clientes(){}

    public Clientes(String nombre, String email, String contraseña, String direccion, String telefono, Date fechaRegistro ){
        super(nombre, email, contraseña);
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
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
}
