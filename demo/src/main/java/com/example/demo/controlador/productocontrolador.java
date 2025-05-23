package com.example.demo.controlador;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import com.example.demo.entidad.productoenty;
import com.example.demo.entidad.proveedorenty;
import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.detallecomprarepositorio;
import com.example.demo.repositorio.productorepositorio;
import com.example.demo.repositorio.proveedorrepositorio;
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

    @Autowired
    public proveedorrepositorio proveedorServicio;

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

        List<productoenty> listaProductos = productoservicio.findAll();
        model.addAttribute("listaProductos", listaProductos);

        // Agrega esta línea:
        List<proveedorenty> proveedores = proveedorServicio.findAll();
        model.addAttribute("proveedores", proveedores);

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

    @PostMapping("/editProducto")
    public String editarProducto(
            @RequestParam int id_producto,
            @RequestParam String nombre,
            @RequestParam int cantidad_en_stock,
            @RequestParam double precio_venta_unitario,
            @RequestParam String url_imagen,
            @RequestParam("proveedorId") Integer proveedorId) {
        try {
            productoenty producto = productoservicio.findById(id_producto).orElse(null);
            if (producto != null) {
                producto.setNombre(nombre);
                producto.setCantidad_en_stock(cantidad_en_stock);
                producto.setPrecio_venta_unitario(precio_venta_unitario);
                producto.setUrl_imagen(url_imagen);
                proveedorenty proveedor = proveedorServicio.findById(proveedorId).orElse(null);
                producto.setProveedor(proveedor);
                productoservicio.save(producto);
            }
        } catch (Exception e) {
            System.out.println("Error al editar producto: " + e.getMessage());
        }
        return "redirect:/Productos";
    }

    @PostMapping("/insertProducto")
    public String insertProducto(
            @Validated productoenty objProducto,
            @RequestParam("proveedorId") Integer proveedorId) {
        proveedorenty proveedor = proveedorServicio.findById(proveedorId).orElse(null);
        objProducto.setProveedor(proveedor);
        productoservicio.save(objProducto);
        return "redirect:/Productos";
    }

}
