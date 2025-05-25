package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entidad.categoriaenty;

public interface categoriarepositorio extends JpaRepository<categoriaenty, Integer> {
    // Puedes agregar métodos personalizados si los necesitas
}
