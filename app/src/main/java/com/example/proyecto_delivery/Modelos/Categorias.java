package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Clase que trae todas las categorias, del servidor.
 */
public class Categorias implements Serializable {
    @SerializedName("IDCategoria")
    private int IDCategoria;
    @SerializedName("Categoria")
    private String Categoria;
    @SerializedName("Imagen")
    private String Imagen;

    public int getIDCategoria() {
        return IDCategoria;
    }

    public void setIDCategoria(int IDCategoria) {
        this.IDCategoria = IDCategoria;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
