package com.example.demo.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entidad.detallePedido;
import com.example.demo.entidad.detallecompra;
import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.detallecomprarepositorio;
import com.example.demo.repositorio.detallepedidorepositorio;
import com.example.demo.repositorio.usuariorepositorio;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class transaccionescontrolador {

    @Autowired
    private detallecomprarepositorio detalleCompraService;

    @Autowired
    private detallepedidorepositorio detallePedidoService;

    @Autowired
    public usuariorepositorio usuarioservicio;

    @GetMapping("/Transacciones")
    public String mostrarFormularioLogin(Model model, HttpServletResponse response, Authentication authentication) {

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
        List<detallecompra> compras = detalleCompraService.findAll();
        List<detallePedido> pedidos = detallePedidoService.findAll();
        List<Map<String, Object>> transacciones = new ArrayList<>();

        // Procesar compras
        for (detallecompra compra : compras) {
            Map<String, Object> t = new HashMap<>();
            t.put("fecha", compra.getFechaCompra().toString()); // Si tienes fecha, cámbiala aquí
            t.put("tipo", "Compra");
            t.put("cantidad", compra.getCantidad()); // Si tienes cantidad, cámbiala aquí
            t.put("costoUnitario", compra.getPrecio_compra_proveedor()); // Si tienes costo, cámbiala aquí
            t.put("producto", compra.getIdProducto() != null ? compra.getIdProducto().getNombre() : "");
            t.put("usuario", compra.getIdProveedor() != null ? compra.getIdProveedor().getNombre_empresa() : "");
            transacciones.add(t);
        }

        // Procesar pedidos
        for (detallePedido pedido : pedidos) {
            Map<String, Object> t = new HashMap<>();
            String fecha = "";
            if (pedido.getIdPedido() != null && pedido.getIdPedido().getFechaCreacion() != null) {
                fecha = pedido.getIdPedido().getFechaCreacion().toString();
            }
            t.put("fecha", fecha); // Si tienes fecha, cámbiala aquí
            t.put("tipo", "Pedido");
            t.put("cantidad", pedido.getCantidadSolicitada());
            t.put("costoUnitario", pedido.getPrecioTotalCompra());
            t.put("producto", pedido.getIdProducto() != null ? pedido.getIdProducto().getNombre() : "");
            t.put("usuario",
                    pedido.getIdPedido() != null ? pedido.getIdPedido().getIdUsuario().getNombreUsuario() : ""); // Completa
                                                                                                                 // si
                                                                                                                 // tienes
                                                                                                                 // usuario
                                                                                                                 // relacionado
            transacciones.add(t);
        }

        model.addAttribute("transacciones", transacciones);

        return "transacciones"; // Si no está autenticado, puede ver el formulario de login
    }

}
