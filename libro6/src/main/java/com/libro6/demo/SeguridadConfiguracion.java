/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libro6.demo;


import com.libro6.demo.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Nnahu
 */

    
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter {
    @Autowired
    public UsuarioServicio usuarioServicio;
    
    @Autowired
    public void cofigurerGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    
    }
    @Override
    protected void configure(HttpSecurity http) throws Error, Exception{
        http.headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                    .antMatchers("/css/", "/js/", "/img/*")
                    .permitAll()
                .and().formLogin()
                    .loginPage("/logear")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("clave")
                        .defaultSuccessUrl("/main")
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .and().csrf()//sin necesidad de estar logueado o tener algún permiso específico
                        .disable();//sin necesidad de estar logueado o tener algún permiso específico

    }
}
