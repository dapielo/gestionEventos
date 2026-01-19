package main.ejercicio3.util;

import main.ejercicio3.model.Evento;
import main.ejercicio3.model.EventoForm;

public class EventoMapper {

    public static EventoForm toEventoForm(Evento evento) {
        EventoForm eventoForm = new EventoForm();
        eventoForm.setId(evento.getId());
        eventoForm.setNombre(evento.getNombre());
        eventoForm.setCompletada(evento.isCompletada());
        eventoForm.setRutaCartel(evento.getRutaCartel());
        return eventoForm;
    }
}
