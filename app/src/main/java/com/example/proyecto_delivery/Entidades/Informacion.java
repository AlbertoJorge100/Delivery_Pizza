package com.example.proyecto_delivery.Entidades;

public class Informacion {
    private String Titulo;
    private String SubTitulo;
    private String Descripcion;
    private String Imagen;

    public Informacion() {
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getSubTitulo() {
        return SubTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        SubTitulo = subTitulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void setImagen(String Imagen){this.Imagen=Imagen;}

    public String getImagen(){
        return this.Imagen;
    }
}
