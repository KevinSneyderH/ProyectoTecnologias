package com.example.demo.controlador;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.example.demo.entidad.compraenty;
import com.example.demo.entidad.detallecompra;
import com.example.demo.entidad.productoenty;
import com.example.demo.entidad.proveedorenty;
import com.example.demo.repositorio.comprarepositorio;
import com.example.demo.repositorio.detallecomprarepositorio;
import com.example.demo.repositorio.productorepositorio;
import com.example.demo.repositorio.proveedorrepositorio;

@Controller
public class compracontrolador {

    @Autowired
    public comprarepositorio compraServicio;

    @Autowired
    public detallecomprarepositorio detalleCompraServicio;

    @Autowired
    public productorepositorio productoServicio;

    @Autowired
    public proveedorrepositorio proveedorServicio;

    @GetMapping("/Compras")
    public String mostrarCompras(Model model, Authentication authentication) {
        // Usuario y rol
        String nombreUsuario = authentication.getName();
        model.addAttribute("nombreUsuario", nombreUsuario);
        model.addAttribute("rolUsuario", "Empleado"); // Ajusta según tu lógica

        // Listado de compras y productos
        List<compraenty> compras = compraServicio.findAll();
        compras.sort((a, b) -> b.getFecha().compareTo(a.getFecha()));
        model.addAttribute("compras", compras);

        List<productoenty> productos = productoServicio.findAll();
        model.addAttribute("productos", productos);

        List<proveedorenty> proveedores = proveedorServicio.findAll();
        model.addAttribute("proveedores", proveedores);

        return "compra";
    }

    @PostMapping("/insertcompra")
    @ResponseBody
    public Map<String, Object> insertarCompra(
            @RequestParam("productoIds") List<Integer> productoIds,
            @RequestParam("cantidades") List<Integer> cantidades,
            @RequestParam("proveedorId") Integer proveedorId,
            Authentication authentication) {

        // Lógica similar a pedidos, pero para compras
        double totalCompra = 0;
        for (int i = 0; i < productoIds.size(); i++) {
            int cantidad = cantidades.get(i);
            if (cantidad > 0) {
                productoenty producto = productoServicio.findById(productoIds.get(i)).orElseThrow();
                double precioUnitario = producto.getPrecio_venta_unitario();
                double subtotal = cantidad * precioUnitario;
                totalCompra += subtotal;
            }
        }

        compraenty compra = new compraenty();
        compra.setFecha(java.sql.Date.valueOf(java.time.LocalDate.now()));
        compra.setEstado("Pendiente");
        compra.setCostoTotal(totalCompra);
        compra.setProveedor(proveedorServicio.findById(proveedorId).orElse(null));
        compra = compraServicio.save(compra);

        for (int i = 0; i < productoIds.size(); i++) {
            int cantidad = cantidades.get(i);
            if (cantidad > 0) {
                productoenty producto = productoServicio.findById(productoIds.get(i)).orElseThrow();
                double precioUnitario = producto.getPrecio_venta_unitario();
                double subtotal = cantidad * precioUnitario;

                detallecompra detalle = new detallecompra();
                detalle.setCompra(compra);
                detalle.setIdProducto(producto);
                detalle.setCantidad(cantidad);
                detalle.setPrecio_compra_proveedor(subtotal);
                detalleCompraServicio.save(detalle);

                // Actualizar stock
                int nuevoStock = producto.getCantidad_en_stock() + cantidad;
                producto.setCantidad_en_stock(nuevoStock);
                productoServicio.save(producto);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id_compra", compra.getId_compra());
        response.put("success", true);
        return response;
    }

    @GetMapping("/api/compras/{id}/detalles")
    @ResponseBody
    public Map<String, Object> getDetallesCompra(@PathVariable("id") int idCompra) {
        List<detallecompra> detalles = detalleCompraServicio.buscarPorIdCompra(idCompra);
        List<Map<String, Object>> responseDetalles = new ArrayList<>();
        double total = 0;
        for (detallecompra d : detalles) {
            Map<String, Object> det = new HashMap<>();
            det.put("productoNombre", d.getIdProducto().getNombre());
            det.put("cantidad", d.getCantidad());
            det.put("subtotal", d.getPrecio_compra_proveedor());
            responseDetalles.add(det);
            total += d.getPrecio_compra_proveedor();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("detalles", responseDetalles);
        response.put("total", total);
        return response;
    }
}