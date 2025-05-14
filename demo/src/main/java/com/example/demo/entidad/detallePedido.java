package com.example.demo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class detallePedido {

    @Id
    @Column(name = "id_detalle_pedido")
    private int id;

    @Column(name = "cantidad_solicitada")
    private int cantidadSolicitada;

    @Column(name = "subtotal")
    private double precioTotalCompra;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private pedidoenty idPedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private productoenty idProducto;

    public detallePedido() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }


    public double getPrecioTotalCompra() {
        return precioTotalCompra;
    }

    public void setPrecioTotalCompra(double precioTotalCompra) {
        this.precioTotalCompra = precioTotalCompra;
    }

    public pedidoenty getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(pedidoenty idPedido) {
        this.idPedido = idPedido;
    }

    public productoenty getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(productoenty idProducto) {
        this.idProducto = idProducto;
    }

}
