package com.example.demo.entidad;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
public class compraenty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_compra;

    @Column(nullable = false)
    private Timestamp fecha;

    @Column(nullable = false)
    private String estado;

    @Column(name = "costo_total")
    private double costoTotal;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_proveedor", nullable = true)
    private proveedorenty proveedor;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private usuarioenty usuario;

    public usuarioenty getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarioenty usuario) {
        this.usuario = usuario;
    }

    // Getters y Setters

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFechaCreacion(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public proveedorenty getProveedor() {
        return proveedor;
    }

    public void setProveedor(proveedorenty proveedor) {
        this.proveedor = proveedor;
    }
}