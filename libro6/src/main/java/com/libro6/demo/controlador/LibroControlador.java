package com.libro6.demo.controlador;


import com.libro6.demo.entidad.Autor;
import com.libro6.demo.entidad.Editorial;
import com.libro6.demo.entidad.Libro;
import com.libro6.demo.servicio.AutorServicio;
import com.libro6.demo.servicio.EditorialServicio;
import com.libro6.demo.servicio.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
public class LibroControlador {
      
    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/cargarLibro")
    public String formularioLibro(ModelMap modelo) {
        modelo.put("tipo", null);
        List<Autor> listadoAutores = autorServicio.listadoAutor();
        List<Editorial> listadoEditorial = editorialServicio.listadoEditorial();
        modelo.addAttribute("autores", listadoAutores);
        modelo.addAttribute("editoriales", listadoEditorial);
        return "cargarLibro.html";
    }

    @PostMapping("/registroLibro")
    public String registroLibro(ModelMap modelo, @RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String autor, @RequestParam(required = false) String editorial) {
        Autor autorObjeto = autorServicio.buscarPorNombre(autor);
        Editorial editorialObjeto = editorialServicio.buscarPorNombre(editorial);
        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, autorObjeto, editorialObjeto);
        } catch (Error ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.addAttribute("autores", autorServicio.listadoAutor());
            modelo.addAttribute("editoriales", editorialServicio.listadoEditorial());
            return "cargarLibro.html";
        }
        modelo.put("mensaje", "Libro cargado con éxito");
        return "index.html";
    }

    @GetMapping("/tablaLibro")
    public String listadoLibros(ModelMap modelo) {
        modelo.addAttribute("lista", libroServicio.listadoLibros());
        return "tablaLibro.html";
    }

    @GetMapping("/libro/{id}")
    public String editarLibro(@PathVariable("id") String id,RedirectAttributes redirectAttributes, ModelMap modelo) {
        Libro libro = libroServicio.buscaLibros(id).get();
        modelo.put("tipo", libro);
        modelo.put("isbn", libro.getIsbn());
        modelo.put("titulo", libro.getTitulo());
        modelo.put("anio", libro.getAnio());
        modelo.put("autores", autorServicio.listadoAutor());
        modelo.put("editoriales", editorialServicio.listadoEditorial());
        return "cargarLibro.html.html";
    }

    @PostMapping("/editarLibro")
    public String cambioLibro(RedirectAttributes redirectAttributes, String id, ModelMap modelo, @RequestParam(required = false) Long isbn, @RequestParam(required = false) String titulo, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String autor, @RequestParam(required = false) String editorial) {
        Libro libro = libroServicio.buscaLibros(id).get();
        Autor autorObjeto = autorServicio.buscarPorNombre(autor);
        Editorial editorialObjeto = editorialServicio.buscarPorNombre(editorial);
        try {
            libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares, autorObjeto, editorialObjeto);
        } catch (Error ex) {
            modelo.put("tipo", libro);
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/libro/{id}";
        }
        modelo.put("mensaje", "Libro modificado con éxito");
        return "index.html";
    }
    
        @GetMapping("libro/baja/{id}")
    public String bajaLibro(@PathVariable("id") String id, ModelMap modelo) throws Error {
        try {
            libroServicio.bajaLibro(id);
        } catch (Error ex) {
            modelo.put("error", ex.getMessage());
            return "tablaLibro";
        }
        return "redirect:/tablaLibro";
    }
    
        @GetMapping("libro/alta/{id}")
    public String altaLibro(@PathVariable("id") String id, ModelMap modelo) throws Error{
        try {
            libroServicio.altaLibro(id);
        } catch (Error ex) {
            modelo.put("error", ex.getMessage());
            return "tablaLibro";
        }

        return "redirect:/tablaLibro";
    }
    

  
    
}