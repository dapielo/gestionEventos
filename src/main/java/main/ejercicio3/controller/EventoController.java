package main.ejercicio3.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import main.ejercicio3.exceptions.CartelException;
import main.ejercicio3.exceptions.EventoException;
import main.ejercicio3.model.Evento;
import main.ejercicio3.model.EventoForm;
import main.ejercicio3.model.FiltroForm;
import main.ejercicio3.service.EventoService;
import main.ejercicio3.util.EventoMapper;

@Controller
public class EventoController {
    @Autowired
    @Qualifier("memory")
    EventoService eventoService;

    // En el GetMapping comprobamos todas las posibilidades que hay con los filtros para añadir una lista u otra al modelo y hacer el return
    // de la vista correspondiente
    @GetMapping("/")
    public String inicio(Model modelo, 
        @RequestParam(required = false) String mensaje, 
        @RequestParam(required = false) Boolean completadas, 
        @RequestParam(required = false) String filtro,
        @ModelAttribute FiltroForm filtroForm
        ){
        if (mensaje != null) {
            if (mensaje.toLowerCase().contains("error:")) {
                modelo.addAttribute("mensaje_error", mensaje);
            } else {
                modelo.addAttribute("mensaje_confirmacion", mensaje);
            }
        }

        modelo.addAttribute("eventos", eventoService.obtenerEventos(filtro,completadas));
        modelo.addAttribute("filtroForm",filtroForm);
        return "listEventos";
    }

    @GetMapping("/delete/{id}")
    public String borrarEvento(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            eventoService.borrarEvento(id);
            redirectAttributes.addFlashAttribute("mensaje", "Evento eliminado correctamente");
        } catch (EventoException e){
            redirectAttributes.addFlashAttribute("mensaje","ERROR: "+e.getMessage());
        }
        redirectAttributes.addAttribute("mensaje","El evento con id "+id+" ha eliminado");
        return "redirect:/";
    }

    @GetMapping("/toggle/{id}")
    public String cambiarEstado(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            eventoService.toggleCompletado(id);
            redirectAttributes.addAttribute("mensaje","El estado ha sido cambiado correctamente");
        } catch (EventoException e){
            redirectAttributes.addAttribute("mensaje","ERROR: "+e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/agregar")
    public String aniadirEvento(@ModelAttribute EventoForm eventoForm){
        return "eventoForm";
    }

    // No tiene que comprobar nada, ya se encarga el servicio, solo tiene que capturar el error o el mensaje y devolverlo a la raiz
    @PostMapping("/agregar")
    public String agregarEvento(@Valid EventoForm eventoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "eventoForm";
        }
        try {
            String mensaje = eventoService.guardarEvento(eventoForm);
            redirectAttributes.addAttribute("mensaje",mensaje);
            return "redirect:/";
        } catch (EventoException | CartelException e){
            redirectAttributes.addAttribute("mensaje",e.getMessage());
            return "redirect:/";
        }
    }

    // Esto solo añade los datos a un DTO que luego le pasa a la vista
    @GetMapping("/editar/{id}")
    public String editarEvento(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try {
            Evento eventoEditar = eventoService.buscarPorId(id);
            model.addAttribute("eventoForm", EventoMapper.toEventoForm(eventoEditar));
            return "eventoForm";
        } catch (EventoException e) {
            redirectAttributes.addAttribute("mensaje",e.getMessage());
            return "redirect:/";
        }
    }
}
