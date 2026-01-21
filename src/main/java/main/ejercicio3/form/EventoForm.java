package main.ejercicio3.form;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;

public class EventoForm {
    private Integer id;
    @NotBlank(message = "el nombre no puede estar vacío")
    private String nombre;
    private Boolean completada;
    private String rutaCartel;
    private MultipartFile cartel;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Boolean getCompletada() {
        return completada;
    }
    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }
    public String getRutaCartel() {
        return rutaCartel;
    }
    public void setRutaCartel(String rutaCartel) {
        this.rutaCartel = rutaCartel;
    }
    public MultipartFile getCartel() {
        return cartel;
    }
    public void setCartel(MultipartFile cartel) {
        this.cartel = cartel;
    }
}
