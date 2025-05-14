
package com.example.demo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class proveedorenty {
    @Id
    private int id_proveedor;
    private String nombre_empresa;
    private String contacto_principal;
    private String telefono;
    private String email;
    private String direccion;

    public proveedorenty() {
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getContacto_principal() {
        return contacto_principal;
    }

    public void setContacto_principal(String contacto_principal) {
        this.contacto_principal = contacto_principal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}