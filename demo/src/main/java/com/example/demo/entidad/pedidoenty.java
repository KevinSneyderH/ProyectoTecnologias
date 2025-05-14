package com.example.demo.entidad;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class pedidoenty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pedido;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_entrega_estimada")
    private Date fechaentrega_estimada;

    @Column(name = "estado_pedido")
    private String estadopedido;

    @Column(name = "costo_total")
    private double costototal;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private usuarioenty idUsuario;

    public pedidoenty() {
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaentrega_estimada() {
        return fechaentrega_estimada;
    }

    public void setFechaentrega_estimada(Date fechaentrega_estimada) {
        this.fechaentrega_estimada = fechaentrega_estimada;
    }

    public String getEstadopedido() {
        return estadopedido;
    }

    public void setEstadopedido(String estadopedido) {
        this.estadopedido = estadopedido;
    }

    public double getCostototal() {
        return costototal;
    }

    public void setCostototal(double costototal) {
        this.costototal = costototal;
    }

    public usuarioenty getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(usuarioenty idUsuario) {
        this.idUsuario = idUsuario;
    }

}
