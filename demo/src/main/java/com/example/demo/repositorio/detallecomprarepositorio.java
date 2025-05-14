package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidad.detallecompra;
import com.example.demo.entidad.productoenty;

public interface detallecomprarepositorio extends JpaRepository<detallecompra, Integer> {

    productoenty deleteById(detallecompra id);
}



