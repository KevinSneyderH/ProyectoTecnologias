package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entidad.detallePedido;
import com.example.demo.entidad.detallecompra;
import com.example.demo.entidad.productoenty;

public interface detallecomprarepositorio extends JpaRepository<detallecompra, Integer> {

    @Query("SELECT d FROM detallecompra d WHERE d.compra.id_compra = :idCompra")
    List<detallecompra> buscarPorIdCompra(@Param("idCompra") int idCompra);
}
