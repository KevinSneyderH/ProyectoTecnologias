package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entidad.compraenty;

public interface comprarepositorio extends JpaRepository<compraenty, Integer> {
    // Puedes agregar métodos personalizados si los necesitas
}
