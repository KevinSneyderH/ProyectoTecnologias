package com.example.demo.controlador;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        // Ejemplo en tu controlador
        List<pedidoenty> pedidos = pedidoservicio.findAll();
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
        pedidoenty pedido = new pedidoenty();
        pedido.setIdUsuario(usuario);
        pedido.setFechaCreacion(java.sql.Date.valueOf(java.time.LocalDate.now()));
        pedido.setEstadopedido("Pendiente");
        pedido = pedidoservicio.save(pedido);

        for (int i = 0; i < productoIds.size(); i++) {
            int cantidad = cantidades.get(i);
            if (cantidad > 0) {
                detallePedido detalle = new detallePedido();
                detalle.setIdPedido(pedido);
                detalle.setIdProducto(productoservicio.findById(productoIds.get(i)).orElseThrow());
                detalle.setCantidadSolicitada(cantidad);
                detallepedidorepositorio.save(detalle);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id_pedido", pedido.getId_pedido());
        response.put("success", true);
        return response;
    }

    @PostMapping("/Pedidos/{id}/agregar-productos")
    public String agregarProductoAlPedido(@PathVariable("id") int idPedido, @ModelAttribute detallePedido detalle) {
        pedidoenty pedido = pedidoservicio.findById(idPedido).orElseThrow();
        detalle.setIdPedido(pedido);
        detallepedidorepositorio.save(detalle);
        return "redirect:/Pedidos/" + idPedido + "/agregar-productos";
    }

}// fin
