package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidad.productoenty;

public interface productorepositorio extends JpaRepository<productoenty, Integer> {

}
