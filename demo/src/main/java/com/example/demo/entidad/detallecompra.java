package com.example.demo.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class detallecompra {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private proveedorenty idProveedor;

    @ManyToOne
    @JoinColumn(name = "idproducto")
    private productoenty idProducto;

    private int cantidad;

    private double precio_compra_proveedor;

    private Date FechaCompra;

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

    public proveedorenty getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(proveedorenty idProveedor) {
        this.idProveedor = idProveedor;
    }

    public productoenty getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(productoenty idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFechaCompra() {
        return FechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        FechaCompra = fechaCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
