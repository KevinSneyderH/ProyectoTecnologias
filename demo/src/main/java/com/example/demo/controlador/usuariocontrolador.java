package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import com.example.demo.entidad.usuarioenty;

import com.example.demo.repositorio.usuariorepositorio;

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
    private BCryptPasswordEncoder passwordEncoder;

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
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies
        // Obtener el nombre de usuario desde la autenticación
        String nombreUsuario = authentication.getName();

        // Buscar el usuario en la base de datos
        usuarioenty usuario = usuarioservicio.findByNombreUsuario(nombreUsuario);

        model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
        model.addAttribute("rolUsuario", usuario.getRol());

        // añade la lista de usuarios a la vista
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
        return "login"; // Redirige al login después del registro exitoso
    }

}
