package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidad.pedidoenty;

public interface pedidorepositorio extends JpaRepository<pedidoenty, Integer> {

}