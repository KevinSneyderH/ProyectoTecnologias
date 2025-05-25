package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entidad.marcaenty;

public interface marcarepositorio extends JpaRepository<marcaenty, Integer> {
    // Puedes agregar métodos personalizados si los necesitas
}
