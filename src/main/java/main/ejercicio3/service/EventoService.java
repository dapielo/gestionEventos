package main.ejercicio3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import main.ejercicio3.exceptions.EventoException;
import main.ejercicio3.model.Evento;

@Service
@Qualifier("memory")
@Primary
public class EventoService {
    List<Evento> eventos = new ArrayList<>();
    int nextId=1;

    public void aniadirEvento(String nombreEvento){
        if (nombreEvento.trim().isEmpty()){
            throw new EventoException("El nombre del evento no puede estar vacío");
        }
        eventos.add(new Evento(nextId++, nombreEvento, false));
    }

    public void borrarEvento(int id){
        boolean existe = eventos.removeIf(e -> e.getId()==id);
        if (!existe){
            throw new EventoException("No existe un evento con ID: "+id);
        }
    }

    public void toggleCompletado(int id){
        boolean existe = false;
        for (Evento e: eventos){
            if (e.getId()==id){
                existe=true;
                e.setCompletada(!e.isCompletada());
                break;
            }
        }
        if (!existe){
            throw new EventoException("No existe un evento con ID: "+id);
        }
    }
    
    // Vamos a sobrecargar el método obtenerEventos para poder no recibir ningun parámetro y devovler todos, recibir solo el boolean de si estan 
    // completados o no, el filtro de nombre, o ambos
    public List<Evento> obtenerEventos(){
        return this.eventos;
    }

    public List<Evento> obtenerEventos(boolean completadas){
        return this.eventos.stream()
                           .filter(e -> e.isCompletada()==completadas)
                           .collect(Collectors.toList());
    }

    public List<Evento> obtenerEventos(String filtro){
        return this.eventos.stream()
                           .filter(e -> e.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                           .collect(Collectors.toList());
    }

    public List<Evento> obtenerEventos(boolean completados, String filtro){
        List<Evento> eventosFiltrados= new ArrayList<>();
        eventosFiltrados.addAll(this.eventos.stream().filter(e -> e.isCompletada()==completados && e.getNombre().toLowerCase().contains(filtro.toLowerCase()))
        .collect(Collectors.toList()));
        return eventosFiltrados;
    }
}
