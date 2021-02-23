package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

public class Productos {
    @SerializedName("IDProducto")
    private int IDProducto=0;
    @SerializedName("Producto")
    private String Producto;
    @SerializedName("Precio")
    private Double Precio;
    @SerializedName("Existencias")
    private int Existencias;
    @SerializedName("Imagen")
    private String Imagen;
    @SerializedName("Descripcion")
    private String Descripcion;
    @SerializedName("IDCategoria")
    private int IDCategoria;

    public void setIDCategoria(int id){
        this.IDCategoria=id;
    }

    public int getIDCategoria(){
        return this.IDCategoria;
    }
    public int getIDProducto() {
        return this.IDProducto;
    }

    public void setIDProducto(int idProducto) {
        this.IDProducto = idProducto;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void setExistencias(int cantidad){this.Existencias=cantidad;}

    public int getExistencias(){return this.Existencias;}

}