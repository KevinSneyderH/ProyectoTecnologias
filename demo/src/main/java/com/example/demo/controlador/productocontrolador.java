package com.example.demo.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import com.example.demo.entidad.productoenty;
import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.detallecomprarepositorio;
import com.example.demo.repositorio.productorepositorio;
import com.example.demo.repositorio.usuariorepositorio;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class productocontrolador {

    @Autowired
    public usuariorepositorio usuarioservicio;

    @Autowired
    public detallecomprarepositorio detallecompraservicio;

    @Autowired

    public productorepositorio productoservicio;

    @GetMapping("/Productos")
    public String mostrarPaginaProductos(Model model, HttpServletResponse response, Authentication authentication) {
        // Desactiva caché
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies
        // Obtener el nombre de usuario desde la autenticación
        String nombreUsuario = authentication.getName();

        // Buscar el usuario en la base de datos
        usuarioenty usuario = usuarioservicio.findByNombreUsuario(nombreUsuario);

        model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
        model.addAttribute("rolUsuario", usuario.getRol());

        List<productoenty> listaProductos = productoservicio.findAll(); // recupera todos los usuarios
        model.addAttribute("listaProductos", listaProductos); // añade la lista de usuarios a la vista// añade la lista
                                                              // de usuarios a la vista
        return "productos";
    }

    @PostMapping("/deleteProducto")
    public String eliminarProducto(@RequestParam int idproducto) {
        try {
            productoservicio.deleteById(idproducto);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return "redirect:/Productos";
    }

    @PostMapping("/insertProducto") // agregar base de datos
    public String insertProducto(@Validated productoenty objProducto) {
        try {
            productoservicio.save(objProducto);
        } catch (Exception e) {
            System.out.println("Error insetar proveedor: " + e.getMessage());
        }
        return "redirect:/Productos";
    }

}
