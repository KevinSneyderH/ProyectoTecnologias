package com.example.demo.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class detallecompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idproducto")
    private productoenty idProducto;

    @ManyToOne
    @JoinColumn(name = "id_compra")
    private compraenty compra;

    private int cantidad;

    private double precio_compra_proveedor;

    public detallecompra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio_compra_proveedor() {
        return precio_compra_proveedor;
    }

    public void setPrecio_compra_proveedor(double precio_compra_proveedor) {
        this.precio_compra_proveedor = precio_compra_proveedor;
    }

    public productoenty getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(productoenty idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public compraenty getCompra() {
        return compra;
    }

    public void setCompra(compraenty compra) {
        this.compra = compra;
    }
}
