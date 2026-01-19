package main.ejercicio3.model;

public class Evento {
    private Integer id;
    private String nombre;
    private boolean completada;
    private String rutaCartel;

    public String getRutaCartel() {
        return rutaCartel;
    }

    public void setRutaCartel(String rutaCartel) {
        this.rutaCartel = rutaCartel;
    }

    public Evento(Integer id, String nombre, boolean completada, String rutaCartel) {
        this.id = id;
        this.nombre = nombre;
        this.completada = completada;
        this.rutaCartel = rutaCartel;
    }

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

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

}
