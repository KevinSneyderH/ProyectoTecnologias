package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entidad.detallePedido;
import com.example.demo.entidad.pedidoenty;

public interface detallepedidorepositorio extends JpaRepository<detallePedido, Integer> {

    void save(pedidoenty objPedido);

}