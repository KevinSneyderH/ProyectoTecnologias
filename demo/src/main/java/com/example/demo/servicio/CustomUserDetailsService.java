package com.example.demo.servicio;

import com.example.demo.entidad.usuarioenty;
import com.example.demo.repositorio.usuariorepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private usuariorepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usuarioenty usuario = usuarioRepositorio.findByNombreUsuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return User.builder()
                .username(usuario.getNombreUsuario()) // ajusta al nombre real del campo
                .password(usuario.getContrasena_encriptada()) // asegúrate que esté encriptado
                .roles("USER") // puedes cambiar o cargar dinámicamente desde la entidad
                .build();
    }
}
