package com.example.proyecto_delivery.Entidades;

public class Informacion {
    private int IdMenu;
    private String Titulo;
    private String Imagen;

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setImagen(String Imagen){this.Imagen=Imagen;}

    public String getImagen(){
        return this.Imagen;
    }

    public void setIdMenu(int id){
        this.IdMenu=id;
    }

    public int getIdMenu(){return this.IdMenu;}
}
