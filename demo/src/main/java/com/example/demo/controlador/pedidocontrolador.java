package com.example.demo.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.demo.entidad.detallePedido;
import com.example.demo.entidad.pedidoenty;
import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.detallepedidorepositorio;
import com.example.demo.repositorio.usuariorepositorio;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class pedidocontrolador {

    @Autowired
    public usuariorepositorio usuarioservicio;

    @Autowired
    public detallepedidorepositorio detallepedidoservicio;

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

        List<detallePedido> listaPedidos = detallepedidoservicio.findAll(); // recupera todos los usuarios
        model.addAttribute("listaPedidos", listaPedidos); // añade la lista de usuarios a la vista

        for (int i = 0; i < listaPedidos.size(); i++) {

            System.out.println(listaPedidos.get(i).getIdPedido().getId_pedido());
        }
        return "pedidos";
    }

    @PostMapping("/insertped") // agregar base de datos
    public String inserpedido(@Validated pedidoenty objPedido) {
        try {
            detallepedidoservicio.save(objPedido);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return "redirect:/Pedidos";
    }

    @GetMapping("/Pedidoss")
    public String listarPedidos(Model model) {
        try {

            List<detallePedido> listaPedidos = detallepedidoservicio.findAll();
            model.addAttribute("listaPedidos", listaPedidos);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return "pedidos";
    }

}// fin
