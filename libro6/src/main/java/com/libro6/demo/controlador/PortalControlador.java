/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libro6.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Nnahu
 */
@Controller
public class PortalControlador {
    @GetMapping("/")
    public String inicio(){
        return"inicio.html";
    }
}
