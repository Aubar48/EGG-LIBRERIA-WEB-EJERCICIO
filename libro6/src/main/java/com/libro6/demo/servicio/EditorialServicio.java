package com.libro6.demo.servicio;


import com.libro6.demo.entidad.Editorial;
import com.libro6.demo.repositorio.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

 @Service
public class EditorialServicio {
 
   @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws Error {

        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);

        editorialRepositorio.save(editorial);
    }

    public void validar(String nombre) throws Error {

        if (nombre == null || nombre.isEmpty()) {
            throw new Error("El campo NOMBRE está vacío");
        }
    }

    @Transactional
    public void modificarEditorial(String id, String nombre) throws Error{
        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = editorialRepositorio.findById(id).get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        } else {
            throw new Error("No se encontró la editorial pedida.");
        }
    }

    @Transactional
    public void altaEditorial(String id) throws Error {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = editorialRepositorio.findById(id).get();
            editorial.setAlta(Boolean.TRUE);
            editorialRepositorio.save(editorial);
        } else {
            throw new Error("No se encontró la editorial pedida.");
        }
    }

    @Transactional
    public void bajaEditorial(String id) throws Error {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = editorialRepositorio.findById(id).get();
            editorial.setAlta(Boolean.FALSE);
            editorialRepositorio.save(editorial);
        } else {
            throw new Error("No se encontró la editorial pedida.");
        }
    }

    public List<Editorial> listadoEditorial() {
        return (List<Editorial>) editorialRepositorio.findAll();
    }

    public Editorial buscarPorNombre(String nombre) {
        return editorialRepositorio.buscarEditorialPorNombre(nombre);
    }

    public Optional<Editorial> buscarPorId(String id) {
        return editorialRepositorio.findById(id);
    }
 
}
