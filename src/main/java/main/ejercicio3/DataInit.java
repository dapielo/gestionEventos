package main.ejercicio3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import main.ejercicio3.service.EventoService;

@Component
public class DataInit implements CommandLineRunner{
    @Autowired
    @Qualifier("memory")
    EventoService eventoService;

    public void run(String... args){
        eventoService.aniadirEvento("Cumpleaños de un amigo");
        eventoService.aniadirEvento("Fiesta de disfraces");
        eventoService.aniadirEvento("Festival en el centro");
        eventoService.aniadirEvento("Cumpleaños de un amigo");
        eventoService.aniadirEvento("Ir al cine");
    }
}
