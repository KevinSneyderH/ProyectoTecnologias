package com.example.demo.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entidad.detallePedido;
import com.example.demo.entidad.detallecompra;
import com.example.demo.repositorio.detallecomprarepositorio;
import com.example.demo.repositorio.detallepedidorepositorio;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class transaccionescontrolador {

    @Autowired
    private detallecomprarepositorio detalleCompraService;

    @Autowired
    private detallepedidorepositorio detallePedidoService;

    @GetMapping("/Transacciones")
    public String mostrarFormularioLogin(Model model, HttpServletResponse response) {

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
