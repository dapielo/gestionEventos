package main.ejercicio3.controller;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import main.ejercicio3.model.Evento;

@Controller
public class Controlador {
    private ArrayList<Evento> eventos = new ArrayList<>();
    private int indice;

    @GetMapping("/")
    public String inicio(Model modelo){
        modelo.addAttribute("eventos", eventos);
        return "index";
    }

    @GetMapping("/add")
    public String añadirEvento(@RequestParam String nombre){
        eventos.add(new Evento(indice++, nombre, false));
        return "redirect:/";
    }
}
