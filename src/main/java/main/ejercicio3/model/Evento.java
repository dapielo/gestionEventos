package main.ejercicio3.model;

public class Evento {
    private int id;
    private String nombre;
    private boolean completada;

    public Evento(int id, String nombre, boolean completada) {
        this.id = id;
        this.nombre = nombre;
        this.completada = completada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
