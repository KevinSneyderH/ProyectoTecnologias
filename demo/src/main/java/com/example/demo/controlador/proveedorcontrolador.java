package com.example.demo.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import com.example.demo.entidad.proveedorenty;

import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.proveedorrepositorio;
import com.example.demo.repositorio.usuariorepositorio;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class proveedorcontrolador {

    @Autowired
    public usuariorepositorio usuarioservicio;

    @Autowired
    public proveedorrepositorio proveedorservicio;

    @GetMapping("/Proveedores")
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

        List<proveedorenty> listaProveedor = proveedorservicio.findAll(); // recupera todos los usuarios
        model.addAttribute("listaProveedor", listaProveedor); // añade la lista de usuarios a la vista
        return "proveedores";
    }

    @PostMapping("/deleteProveedor")
    public String eliminarProducto(@RequestParam int idproveedor) {
        try {
            proveedorservicio.deleteById(idproveedor);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return "redirect:/Proveedores";
    }

    @PostMapping("/editProveedor") // agregar base de datos
    public String updateProveedor(@Validated proveedorenty objProvedor) {
        try {
            proveedorservicio.save(objProvedor);
        } catch (Exception e) {
            System.out.println("Error insetar proveedor: " + e.getMessage());
        }
        return "redirect:/Proveedores";
    }

    @PostMapping("/insertProveedor") // agregar base de datos
    public String insertProveedor(@Validated proveedorenty objProvedor) {
        try {
            proveedorservicio.save(objProvedor);
        } catch (Exception e) {
            System.out.println("Error insetar proveedor: " + e.getMessage());
        }
        return "redirect:/Proveedores";
    }

}
