package main.ejercicio3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import main.ejercicio3.model.EventoForm;
import main.ejercicio3.service.EventoService;

@Component
public class DataInit implements CommandLineRunner{
    @Autowired
    @Qualifier("memory")
    EventoService eventoService;

    public void run(String... args){
        EventoForm evento = new EventoForm();
        evento.setCompletada(false);
        evento.setNombre("Cumpleaños de un amigo");
        eventoService.guardarEvento(evento);
        evento.setNombre("Festival en el centro");
        eventoService.guardarEvento(evento);
        evento.setNombre("Fiesta de disfraces");
        eventoService.guardarEvento(evento);
        evento.setNombre("Ir al cine");
        eventoService.guardarEvento(evento);
    }
}
