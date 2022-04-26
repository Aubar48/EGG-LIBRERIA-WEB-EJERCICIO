package com.libro6.demo.servicio;

import com.libro6.demo.entidad.Usuario;
import com.libro6.demo.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional(propagation = Propagation.NESTED)
    public void registrar(String nombre, String apellido, String email, String clave) throws Error {
        validar(nombre, apellido, email, clave);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);    
        
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
                
        usuario.setAlta(new Date());
           
        usuarioRepositorio.save(usuario);
    }
    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, String nombre, String apellido, String email, String clave) throws Error {
        validar(nombre, apellido, email, clave);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setClave(clave);

            usuarioRepositorio.save(usuario);
        } else {
            throw new Error("No se encontro el usuario solicitado");
        }
    }

    private void validar(String nombre, String apellido, String email, String clave) throws Error {
        if (nombre == null || nombre.isEmpty()) {
            throw new Error("El nombre del usuario no puede ser nulo.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Error("El apellido del usuario no puede ser nulo.");
        }
        if (email == null || email.isEmpty()) {
            throw new Error("El email del usuario no puede ser nulo.");
        }
        if (clave == null || clave.isEmpty() || clave.length() <= 3) {
            throw new Error("La clave del usuario no puede ser nulo y debe tener mas de tres digitos.");
        }
    }
    @Transactional(propagation = Propagation.NESTED)
    public void deshabilitar(String id) throws Error {
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());

            usuarioRepositorio.save(usuario);
        } else {
            throw new Error("No se encontro el usuario solicitado");
        }
    }
    @Transactional(propagation = Propagation.NESTED)
    public void habilitar(String id) throws Error {
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);

            usuarioRepositorio.save(usuario);
        } else {
            throw new Error("No se encontro el usuario solicitado");
        }
    }
    

    
@Override
public UserDetails loadUserByUsername (String email) throws Error {
Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
if (usuario != null) {
List<GrantedAuthority> permisos = new ArrayList<>();
GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
permisos.add(p1);
//Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
ServletRequestAttributes attr= (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
HttpSession session = attr.getRequest().getSession();
session.setAttribute("usuariosession", usuario);   
User user = new User(usuario.getEmail(), usuario.getClave(), permisos);
            return user;
}else{
               return null;
        }     
}
}


