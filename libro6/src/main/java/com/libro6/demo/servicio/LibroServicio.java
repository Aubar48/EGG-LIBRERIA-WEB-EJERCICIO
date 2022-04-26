package com.libro6.demo.servicio;


 

import com.libro6.demo.entidad.Autor;
import com.libro6.demo.entidad.Editorial;
import com.libro6.demo.entidad.Libro;
import com.libro6.demo.repositorio.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

 @Service
public class LibroServicio {
      @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Error {

        validar(isbn, titulo, anio, ejemplares, autor, editorial);

        Libro libro = new Libro();
        libro.setAlta(Boolean.TRUE);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Error {

        if (isbn == null) {
            throw new Error("El campo ISBN está vacío");
        }
        if (titulo == null) {
            throw new Error("El campo TITULO está vacío");
        }
        if (anio == null) {
            throw new Error("El campo AÑO está vacío");
        }
        if (ejemplares == null) {
            throw new Error("El campo EJEMPLARES está vacío");
        }

        if (autor == null) {
            throw new Error("Debe cargar el nombre del autor");
        }
        if (editorial == null) {
            throw new Error("Debe cargar el nombre de la editorial");
        }

    }

    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Error {
        validar(isbn, titulo, anio, ejemplares, autor, editorial);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);
        } else {
            throw new Error("No se encontró el libro pedido.");
        }
    }

    @Transactional
    public void altaLibro(String id) throws Error {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = libroRepositorio.findById(id).get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);
        } else {
            throw new Error("No se encontró el libro pedido.");
        }
    }

    @Transactional
    public void bajaLibro(String id) throws Error {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = libroRepositorio.findById(id).get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);
        } else {
            throw new Error("No se encontró el libro pedido.");
        }
    }

    public List<Libro> listadoLibros() {
        return (List<Libro>) libroRepositorio.findAll();
    }

    public Optional<Libro> buscaLibros(String id) {
        return (Optional<Libro>) libroRepositorio.findById(id);
    }

    public Libro buscaLibrosPorTitulo(String titulo) {
        return libroRepositorio.buscarLibroPorTitulo(titulo);
    }
 
 
    }
 