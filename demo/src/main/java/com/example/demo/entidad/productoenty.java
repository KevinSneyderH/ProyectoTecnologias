package com.example.demo.entidad;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class productoenty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;
    private String nombre;
    private int cantidad_en_stock;
    private double precio_venta_unitario;
    private String url_imagen;

    @ManyToOne
    @JoinColumn(name = "marca")
    private marcaenty idmarca;

    @ManyToOne
    @JoinColumn(name = "categoría")
    private categoriaenty idcategoria;

    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<detallecompra> detallesCompra;

    
    public productoenty() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad_en_stock() {
        return cantidad_en_stock;
    }

    public void setCantidad_en_stock(int cantidad_en_stock) {
        this.cantidad_en_stock = cantidad_en_stock;
    }

    public double getPrecio_venta_unitario() {
        return precio_venta_unitario;
    }

    public void setPrecio_venta_unitario(double precio_venta_unitario) {
        this.precio_venta_unitario = precio_venta_unitario;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public List<Timestamp> getFechasDeCompra() {
        if (this.detallesCompra == null) {
            return List.of();
        }
        return this.detallesCompra.stream()
                .filter(detalle -> detalle.getCompra() != null)
                .map(detalle -> detalle.getCompra().getFecha())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<detallecompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<detallecompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

    public marcaenty getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(marcaenty idmarca) {
        this.idmarca = idmarca;
    }

    public categoriaenty getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(categoriaenty idcategoria) {
        this.idcategoria = idcategoria;
    }

  

}
