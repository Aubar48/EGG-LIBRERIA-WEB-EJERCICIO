/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libro6.demo.controlador;

import com.libro6.demo.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nnahu
 */

@Controller
//@RequestMapping("")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
   
    
    @PreAuthorize("hasAnyRole ('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/main")
    public String index() {
        return "index.html";
    }
//   
//    
 @GetMapping ("/logear")
public String login (@RequestParam (required = false) String error, @RequestParam (required = false) String logout, ModelMap model) {
if (error != null) {
model.put("error", "Email de usuario o clave incorrectos.");
}
if(logout != null) {
model.put("logout", "Ha salido correctamente de la plataforma.");
}
return "logg.html";
}


@GetMapping("/registro")
    public String registro() {
        return "registro.html";
    } 
    
    
@PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String clave) throws Error {
        try {                 
            usuarioServicio.registrar(nombre, apellido, email, clave);
                  return "redirect:/";
        } catch (Error ex) {
           modelo.put("Error", ex.getMessage());
           modelo.put("nombre:", nombre);
           modelo.put("apellido:", apellido);
           modelo.put("clave:", clave);
                  return "registro.html";
        }   
       
    }

   

}
