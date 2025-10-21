package main.ejercicio3.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import main.ejercicio3.model.Evento;

@Controller
public class Controlador {

    @GetMapping("/")
    public String inicio(Model modelo){
        List<Evento> eventos = Arrays.asList(
            new Evento(1, "Cumpleaños de un amigo", false),
            new Evento(2, "Fiesta de disfraces", false),
            new Evento(3, "Festival en el centro", false),
            new Evento(4, "Ir al cine", false)
        );
        modelo.addAttribute("eventos", eventos);
        return "index";
    }
}
