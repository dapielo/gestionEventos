package main.ejercicio3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import main.ejercicio3.exceptions.CartelException;
import main.ejercicio3.exceptions.EventoException;
import main.ejercicio3.model.Evento;
import main.ejercicio3.model.EventoForm;

@Service
@Qualifier("memory")
@Primary
public class EventoService {
    @Autowired
    FileService fileService;
    List<Evento> eventos = new ArrayList<>();
    int nextId=1;

    public void borrarEvento(int id){
        boolean existe = eventos.removeIf(e -> e.getId()==id);
        if (!existe){
            throw new EventoException("No existe un evento con ID: "+id);
        }
    }

    // Este mismo evento gestiona las ediciones y las adiciones mirando si eventoForm tiene id o no
    public String guardarEvento(EventoForm eventoForm) throws EventoException, CartelException{
        // Para la comprobacion de que no se repita el nombre, buscamos un evento con diferente id pero con mismo nombre y si lo encuentro lanzo excepcion
        eventos.forEach(e -> {
            if (e.getId()!=eventoForm.getId() && e.getNombre().equalsIgnoreCase(eventoForm.getNombre())){
                throw new EventoException("ERROR: Ya existe un evento con ese nombre");
            }
        });
        if (eventoForm.getId()!=null) {
            Evento eventoEditar = buscarPorId(eventoForm.getId());
            eventoEditar.setNombre(eventoForm.getNombre());
            eventoEditar.setCompletada(eventoForm.getCompletada());
            // Miramos si el form tiene rutaCartel, si la tiene la cambiamos, si no la tiene dejamos la antigua
            // NOTA: Si no seleccionamos ninguna imagen, el form no tiene null en ese campo, tiene un MultipartFile vacío, por eso usamos isEmpty
            String rutaCartelNueva = (eventoForm.getCartel()==null || eventoForm.getCartel().isEmpty())?eventoEditar.getRutaCartel():fileService.guardarCartel(eventoForm.getCartel());
            eventoEditar.setRutaCartel(rutaCartelNueva);
            return eventoEditar.getNombre()+" ha sido editado correctamente";
        }
        // Si el id es null añadimos nuevo evento, CUIDADO: hay que mirar si viene con cartel para generar una ruta con fileService o dejarlo null
        String rutaCartel = (eventoForm.getCartel()==null || eventoForm.getCartel().isEmpty())?null:fileService.guardarCartel(eventoForm.getCartel());
        eventos.add(new Evento(nextId++, eventoForm.getNombre(), eventoForm.getCompletada(), rutaCartel));
        return "Evento añadido correctamente";
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

    public List<Evento> obtenerEventos(String filtro, Boolean completadas){
        return eventos.stream().filter(e -> {
            if (filtro!=null){
                if (!e.getNombre().toLowerCase().trim().contains(filtro.toLowerCase().trim())){
                    return false;
                }
            }
            if (completadas!=null){
                return e.isCompletada()==completadas;
            }
            return true;
        }).toList();
    }

    public Evento buscarPorId(Integer id) throws EventoException {
        try{
            return eventos.stream().filter(e -> e.getId()==id).findAny().orElseThrow();
        } catch (NoSuchElementException e){
            throw new EventoException("ERROR: No existe un evento con id "+id);
        }
    }
}
