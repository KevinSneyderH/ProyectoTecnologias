package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.detallecomprarepositorio;
import com.example.demo.repositorio.detallepedidorepositorio;
import com.example.demo.repositorio.productorepositorio;
import com.example.demo.repositorio.usuariorepositorio;
import com.example.demo.entidad.detallecompra;
import com.example.demo.entidad.productoenty;
import com.example.demo.entidad.detallePedido;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class usuariocontrolador {

    @Autowired
    public usuariorepositorio usuarioservicio;

    @Autowired
    private detallecomprarepositorio detalleCompraService;

    @Autowired
    private detallepedidorepositorio detallePedidoService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private productorepositorio productoservicio;

    @GetMapping("/login")
    public String mostrarFormularioLogin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/Principal"; // Ya está autenticado, lo mandamos a la página principal
        }
        return "login"; // Si no está autenticado, puede ver el formulario de login
    }

    @GetMapping("/Principal")
    public String mostrarPaginaUsuario(Model model, HttpServletResponse response, Authentication authentication) {
        // Desactiva caché
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        String nombreUsuario = authentication.getName();
        usuarioenty usuario = usuarioservicio.findByNombreUsuario(nombreUsuario);

        model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
        model.addAttribute("rolUsuario", usuario.getRol());

        List<detallecompra> compras = detalleCompraService.findAll();
        List<detallePedido> pedidos = detallePedidoService.findAll();

        List<Map<String, Object>> transacciones = new ArrayList<>();

        // Procesar compras
        for (detallecompra compra : compras) {
            Map<String, Object> t = new HashMap<>();
            t.put("fecha", compra.getCompra().getFecha()); // Timestamp directamente
            t.put("tipo", "Compra");
            t.put("cantidad", compra.getCantidad());
            t.put("costoUnitario", compra.getPrecio_compra_proveedor());
            t.put("producto", compra.getIdProducto() != null ? compra.getIdProducto().getNombre() : "");
            t.put("usuario", compra.getCompra().getUsuario().getNombreUsuario() != null
                    ? compra.getCompra().getUsuario().getNombreUsuario()
                    : "");
            transacciones.add(t);
        }

        // Procesar pedidos
        for (detallePedido pedido : pedidos) {
            Map<String, Object> t = new HashMap<>();
            Timestamp fecha = null;
            if (pedido.getIdPedido() != null && pedido.getIdPedido().getFechaCreacion() != null) {
                fecha = pedido.getIdPedido().getFechaCreacion(); // Timestamp directamente
            }
            t.put("fecha", fecha);
            t.put("tipo", "Pedido");
            t.put("cantidad", pedido.getCantidadSolicitada());
            t.put("costoUnitario", pedido.getPrecioTotalCompra());
            t.put("producto", pedido.getIdProducto() != null ? pedido.getIdProducto().getNombre() : "");
            t.put("usuario", pedido.getIdPedido() != null
                    ? pedido.getIdPedido().getIdUsuario().getNombreUsuario()
                    : "");
            transacciones.add(t);
        }

        // Ordenar por fecha descendente usando Timestamp
        transacciones = transacciones.stream()
                .filter(t -> t.get("fecha") != null)
                .sorted((t1, t2) -> ((Timestamp) t2.get("fecha")).compareTo((Timestamp) t1.get("fecha")))
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("transacciones", transacciones);

        List<productoenty> productosBajoStock = productoservicio.findAll()
                .stream()
                .filter(p -> p.getCantidad_en_stock() < 10)
                .toList();

        model.addAttribute("productosBajoStock", productosBajoStock);

        return "principal";
    }

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro"; // Este es tu archivo HTML de registro (asegurate de tenerlo)
    }

    // Método para registrar usuario
    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombre_usuario,
            @RequestParam String email,
            @RequestParam String contrasena,
            @RequestParam String rol,
            @RequestParam int activo,
            Model model) {
        // Verificar que ninguno de los campos obligatorios esté vacío
        if (nombre_usuario == null || nombre_usuario.isEmpty() ||
                email == null || email.isEmpty() ||
                contrasena == null || contrasena.isEmpty() ||
                rol == null || rol.isEmpty() ||
                activo < 0) {
            model.addAttribute("error", "Todos los campos son obligatorios");
            return "registro"; // Regresa al formulario si algún campo está vacío
        }

        // Encriptar la contraseña
        String contrasenaEncriptada = passwordEncoder.encode(contrasena);

        // Crear el nuevo usuario
        usuarioenty nuevoUsuario = new usuarioenty();
        nuevoUsuario.setNombreUsuario(nombre_usuario);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContrasena_encriptada(contrasenaEncriptada);
        nuevoUsuario.setRol(rol);

        // Guardar el usuario en la base de datos
        usuarioservicio.save(nuevoUsuario);

        model.addAttribute("mensaje", "Usuario registrado con éxito");

        // ...existing code...
        // Obtener productos con stock bajo

        return "login"; // Redirige al login después del registro exitoso
    }

}
