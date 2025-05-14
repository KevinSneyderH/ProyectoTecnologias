package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidad.usuarioenty;

public interface usuariorepositorio extends JpaRepository<usuarioenty, Integer> {
    usuarioenty findByNombreUsuario(String nombreUsuario);

}
