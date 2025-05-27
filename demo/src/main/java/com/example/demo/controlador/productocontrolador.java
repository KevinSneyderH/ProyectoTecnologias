package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import com.example.demo.entidad.productoenty;
import com.example.demo.entidad.usuarioenty;
import com.example.demo.entidad.categoriaenty;
import com.example.demo.entidad.marcaenty;
import com.example.demo.repositorio.categoriarepositorio;
import com.example.demo.repositorio.detallecomprarepositorio;
import com.example.demo.repositorio.marcarepositorio;
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

    @Autowired
    public marcarepositorio marcaservicio;

    @Autowired
    public categoriarepositorio categoriaservicio;

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

        List<marcaenty> listaMarcas = marcaservicio.findAll();
        model.addAttribute("listaMarcas", listaMarcas);

        List<categoriaenty> listaCategorias = categoriaservicio.findAll();
        model.addAttribute("listaCategorias", listaCategorias);

        model.addAttribute("paginaActual", "Productos");

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
            @RequestParam double precio_venta_unitario,
            @RequestParam String url_imagen) {
        try {
            productoenty producto = productoservicio.findById(id_producto).orElse(null);
            if (producto != null) {
                producto.setNombre(nombre);
                producto.setPrecio_venta_unitario(precio_venta_unitario);

                // Buscar y asignar la categoría y marca

                producto.setUrl_imagen(url_imagen);
                productoservicio.save(producto);
            }
        } catch (Exception e) {
            System.out.println("Error al editar producto: " + e.getMessage());
        }
        return "redirect:/Productos";
    }

    @PostMapping("/insertProducto")
    public String insertProducto(
            @RequestParam String nombre,
            @RequestParam double precio_venta_unitario,
            @RequestParam int categoria,
            @RequestParam int marca,
            @RequestParam String url_imagen) {
        try {
            productoenty producto = new productoenty();
            producto.setNombre(nombre);
            producto.setPrecio_venta_unitario(precio_venta_unitario);
            producto.setUrl_imagen(url_imagen);

            // Buscar y asignar la categoría y marca
            categoriaenty catObj = categoriaservicio.findById(categoria).orElse(null);
            marcaenty marcaObj = marcaservicio.findById(marca).orElse(null);

            producto.setIdcategoria(catObj);
            producto.setIdmarca(marcaObj);

            productoservicio.save(producto);
        } catch (Exception e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
        return "redirect:/Productos";
    }

}
