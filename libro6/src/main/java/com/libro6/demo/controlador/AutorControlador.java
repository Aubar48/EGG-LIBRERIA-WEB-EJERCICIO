/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libro6.demo.controlador;
import com.libro6.demo.entidad.Autor;
import com.libro6.demo.error.Error;
import com.libro6.demo.servicio.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 *
 * @author Nnahu
 */
@Controller
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/cargarAutor")
    public String agregar(){
        return"cargarAutor.html";
    }
    @PostMapping("/registroAutor")
    public String registroAutor(ModelMap modelo, @RequestParam(required = false) String nombre) throws Error{
        try{
            autorServicio.crearAutor(nombre);
            modelo.put("mensaje", "Autor cargado exitosamente");
            return "Index.html";
        } catch(Error ex){
           modelo.put("Error", ex.getMessage());
           modelo.put("nombre:", nombre);           
            return "cargarAutor.html";
        }
       
    }
    
    @GetMapping("/tablaAutor")
    public String listadoAutores(ModelMap modelo) {
        modelo.addAttribute("lista", autorServicio.listadoAutor());
        return "tablaAutor.html";
    }

    @GetMapping("/autor/{id}")
    public String editarAutor(RedirectAttributes redirectAttributes, @PathVariable("id") String id, ModelMap modelo) {
        Autor autor = autorServicio.buscarPorId(id).get();
        modelo.put("tipo", autor);
        modelo.put("nombre", autor.getNombre());
        return "cargarAutor.html";
    }

    @PostMapping("/edicionAutor")
    public String editamosAutor(RedirectAttributes redirectAttributes, ModelMap modelo, String id, @RequestParam(required = false) String nombreAutor) {
        Autor autor = autorServicio.buscarPorId(id).get();
        try {
            modelo.put("nombreAutor", autor.getNombre());
            autorServicio.modificarAutor(id, nombreAutor);
        } catch (Error ex) {
            modelo.put("tipo", autor);
            modelo.put("error", ex.getMessage());
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/autor/{id}";
        }
        modelo.put("mensaje", "Autor modificado con éxito");
        return "index.html";
    }

    @GetMapping("autor/baja/{id}")
    public String bajaAutor(@PathVariable("id") String id, ModelMap modelo) throws Error {
        try {
            autorServicio.bajaAutor(id);
        } catch (Error ex) {
            modelo.put("error", ex.getMessage());
            return "tablaAutor";
        }
        return "redirect:/tablaAutor";
    }
    
        @GetMapping("autor/alta/{id}")
    public String altaAutor(@PathVariable("id") String id, ModelMap modelo) throws Error {
        try {
            autorServicio.altaAutor(id);
        } catch (Error ex) {
            modelo.put("error", ex.getMessage());
            return "tablaAutor";
        }

        return "redirect:/tablaAutor";
    }
    
}
