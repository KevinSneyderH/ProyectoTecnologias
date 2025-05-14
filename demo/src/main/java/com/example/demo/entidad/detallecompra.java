package com.example.demo.entidad;

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

    public detallecompra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    

    

}
