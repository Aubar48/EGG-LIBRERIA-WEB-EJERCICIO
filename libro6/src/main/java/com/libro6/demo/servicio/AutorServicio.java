package com.libro6.demo.servicio;



import com.libro6.demo.entidad.Autor;
import com.libro6.demo.repositorio.AutorRepositorio;
import java.util.List;
import com.libro6.demo.error.Error;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 
@Service
public class AutorServicio {
 
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre2) throws Error {
        validar(nombre2);
        Autor autor = new Autor();
        autor.setNombre(nombre2);
        autor.setAlta(Boolean.TRUE);

        autorRepositorio.save(autor);
    }

    public void validar(String nombre) throws Error {
//trim quita espaciado, empty verifica si esta vacia.
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Error("El campo NOMBRE está vacío");
        }
    }

    @Transactional
    public void modificarAutor(String id, String nombre) throws Error {
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = autorRepositorio.findById(id).get();           
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor);
        } else {
            throw new Error("No se encontró la editorial pedida.");
        }
    }

    @Transactional
    public void altaAutor(String id) throws Error {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = autorRepositorio.findById(id).get();
            autor.setAlta(Boolean.TRUE);
            autorRepositorio.save(autor);
        } else {
            throw new Error("No se encontró la editorial pedida.");
        }
    }

    @Transactional
    public void bajaAutor(String id) throws Error {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = autorRepositorio.findById(id).get();
            autor.setAlta(Boolean.FALSE);
            autorRepositorio.save(autor);
        } else {
            throw new Error("No se encontró la editorial pedida.");
        }
    }

    public List<Autor> listadoAutor() {
        return (List<Autor>) autorRepositorio.findAll();
    }

    public Autor buscarPorNombre(String nombre) {
        return autorRepositorio.buscarAutorPorNombre(nombre);
    }

    public Optional<Autor> buscarPorId(String id) {
        return autorRepositorio.findById(id);
    }
}
