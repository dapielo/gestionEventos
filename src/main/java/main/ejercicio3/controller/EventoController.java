package main.ejercicio3.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import main.ejercicio3.exceptions.EventoException;
import main.ejercicio3.service.EventoService;

@Controller
public class EventoController {
    @Autowired
    @Qualifier("memory")
    EventoService eventoService;

    // En el GetMapping comprobamos todas las posibilidades que hay con los filtros para añadir una lista u otra al modelo y hacer el return
    // de la vista correspondiente
    @GetMapping("/")
    public String inicio(Model modelo, @RequestParam(required = false) Boolean completadas, @RequestParam(required = false) String filtro){
        // No hay filtro de completadas ni filtro de nombre
        if (completadas==null && filtro==null){
            modelo.addAttribute("eventos", eventoService.obtenerEventos());
            return "listEventos";
        }
        // si paso el anterior if puede ser que completadas sea null entonces filtro de nombre no esta vacio, llamamos al método correspondiente
        if (completadas==null){
            modelo.addAttribute("eventos", eventoService.obtenerEventos(filtro));
            return "listEventos";
        }
        // si paso lso dos anteriores puede ser que filtro sea null y completadas no lo sería así que llamamos al método que acepta un boolean
        if (filtro==null){
            modelo.addAttribute("eventos", eventoService.obtenerEventos(completadas));
            return "listEventos";
        }
        // si ha pasado las tres condiciones anteriores es que ni completadas es null ni filtro es null, llamamos al método que acepta boolean y string
        modelo.addAttribute("eventos", eventoService.obtenerEventos(completadas, filtro));
        return "listEventos";
    }

    // Vamos a añadir el mensaje desde el endpoint en el que se genera, para esto vamos a usar redirectAttributes con el método addFlashAttribute
    // que nos permite que el atributo que añadimos se almacene durante una sola redirección y lo pase al modelo al redirigir con addAttribute
    // no funciona, y a mayores como se puede ver no hace falta añadirlo manualmente al modelo, ya se encarga el redirectAttributes de hacerlo
    // addFlashAttributes lo añade a un almacen temporal durante una redireccion (no a la URL) pero addAttribute lo añade a la url de redireccion
    @GetMapping("/add")
    public String añadirEvento(@RequestParam String nombre, RedirectAttributes redirectAttributes){
        try{
            eventoService.aniadirEvento(nombre);
            redirectAttributes.addFlashAttribute("mensaje", "El evento ha ido añadido correctamente");
        } catch (EventoException e){
            redirectAttributes.addFlashAttribute("mensaje", "ERROR: "+e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String borrarEvento(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            eventoService.borrarEvento(id);
            redirectAttributes.addFlashAttribute("mensaje", "Evento eliminado correctamente");
        } catch (EventoException e){
            redirectAttributes.addFlashAttribute("mensaje","ERROR: "+e.getMessage());
        }
       return "redirect:/";
    }

    @GetMapping("/toggle/{id}")
    public String cambiarEstado(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            eventoService.toggleCompletado(id);
            redirectAttributes.addFlashAttribute("mensaje","El estado ha sido cambiado correctamente");
        } catch (EventoException e){
            redirectAttributes.addFlashAttribute("mensaje","ERROR: "+e.getMessage());
        }
        return "redirect:/";
    }


}
