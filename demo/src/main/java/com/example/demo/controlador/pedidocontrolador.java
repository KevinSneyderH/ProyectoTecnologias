package com.example.demo.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.demo.entidad.detallePedido;
import com.example.demo.entidad.pedidoenty;
import com.example.demo.entidad.productoenty;
import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.detallepedidorepositorio;
import com.example.demo.repositorio.pedidorepositorio;
import com.example.demo.repositorio.productorepositorio;
import com.example.demo.repositorio.usuariorepositorio;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class pedidocontrolador {

    @Autowired
    public usuariorepositorio usuarioservicio;

    @Autowired
    public pedidorepositorio pedidoservicio;

    @Autowired
    public productorepositorio productoservicio;

    @Autowired
    public detallepedidorepositorio detallepedidorepositorio;

    @GetMapping("/Pedidos")
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

        // Obtener y ordenar los pedidos del más reciente al más antiguo
        List<pedidoenty> pedidos = pedidoservicio.findAll();
        pedidos.sort((a, b) -> b.getFechaCreacion().compareTo(a.getFechaCreacion()));
        model.addAttribute("pedidos", pedidos);

        List<productoenty> productos = productoservicio.findAll();
        model.addAttribute("productos", productos);

        return "pedidos";
    }

    @PostMapping("/insertped")
    @ResponseBody
    public Map<String, Object> inserpedido(
            @RequestParam("productoIds") List<Integer> productoIds,
            @RequestParam("cantidades") List<Integer> cantidades,
            Authentication authentication) {
        String nombreUsuario = authentication.getName();
        usuarioenty usuario = usuarioservicio.findByNombreUsuario(nombreUsuario);

        // 1. Calcular el total del pedido
        double totalPedido = 0;
        for (int i = 0; i < productoIds.size(); i++) {
            int cantidad = cantidades.get(i);
            if (cantidad > 0) {
                productoenty producto = productoservicio.findById(productoIds.get(i)).orElseThrow();
                double precioUnitario = producto.getPrecio_venta_unitario();
                double subtotal = cantidad * precioUnitario;
                totalPedido += subtotal;
            }
        }

        // 2. Crear el pedido con el total
        pedidoenty pedido = new pedidoenty();
        pedido.setIdUsuario(usuario);
        pedido.setFechaCreacion(java.sql.Date.valueOf(java.time.LocalDate.now()));
        pedido.setEstadopedido("Pendiente");
        pedido.setCostototal(totalPedido);
        pedido = pedidoservicio.save(pedido);

        // 3. Crear los detalles con su subtotal y actualizar stock
        for (int i = 0; i < productoIds.size(); i++) {
            int cantidad = cantidades.get(i);
            if (cantidad > 0) {
                productoenty producto = productoservicio.findById(productoIds.get(i)).orElseThrow();
                double precioUnitario = producto.getPrecio_venta_unitario();
                double subtotal = cantidad * precioUnitario;

                // Crear detalle del pedido
                detallePedido detalle = new detallePedido();
                detalle.setIdPedido(pedido);
                detalle.setIdProducto(producto);
                detalle.setCantidadSolicitada(cantidad);
                detalle.setPrecioTotalCompra(subtotal);
                detallepedidorepositorio.save(detalle);

                // Actualizar stock del producto
                int nuevoStock = producto.getCantidad_en_stock() + cantidad;
                producto.setCantidad_en_stock(nuevoStock);
                productoservicio.save(producto);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id_pedido", pedido.getId_pedido());
        response.put("success", true);
        return response;
    }

    @GetMapping("/api/pedidos/{id}/detalles")
    @ResponseBody
    public Map<String, Object> getDetallesPedido(@PathVariable("id") int idPedido) {
        List<detallePedido> detalles = detallepedidorepositorio.buscarPorIdPedido(idPedido);
        List<Map<String, Object>> responseDetalles = new ArrayList<>();
        double total = 0;
        for (detallePedido d : detalles) {
            Map<String, Object> det = new HashMap<>();
            det.put("productoNombre", d.getIdProducto().getNombre());
            det.put("cantidadSolicitada", d.getCantidadSolicitada());
            det.put("subtotal", d.getPrecioTotalCompra());
            responseDetalles.add(det);
            total += d.getPrecioTotalCompra();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("detalles", responseDetalles);
        response.put("total", total);
        return response;
    }

}
