package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entidad.detallePedido;

public interface detallepedidorepositorio extends JpaRepository<detallePedido, Integer> {
    @Query("SELECT d FROM detallePedido d WHERE d.idPedido.id_pedido = :idPedido")
    List<detallePedido> buscarPorIdPedido(@Param("idPedido") int idPedido);
}