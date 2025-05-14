package com.example.demo.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class usuarioenty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    private String contrasena_encriptada;

    @Column(nullable = false)
    private String rol;

    private String fecha_creacion;
    private String fecha_actualizacion;

    @PrePersist
    protected void onCreate() {
        String ahora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.fecha_creacion = ahora;
        this.fecha_actualizacion = ahora;
    }

    @PreUpdate
    protected void onUpdate() {
        this.fecha_actualizacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public usuarioenty() {
    }

    public int getId() {
        return id_usuario;
    }

    public void setId(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena_encriptada() {
        return contrasena_encriptada;
    }

    public void setContrasena_encriptada(String contrasena_encriptada) {
        this.contrasena_encriptada = contrasena_encriptada;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena_encriptada;
    }

    public void setContrasena(String contrasena_encriptada) {
        this.contrasena_encriptada = contrasena_encriptada;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFechacreacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFechaactulizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actulizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

}
