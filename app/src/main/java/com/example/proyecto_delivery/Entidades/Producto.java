package com.example.proyecto_delivery.Entidades;

public class Producto {
    private int idProducto=0;
    private int Cantidad;
    private String Producto;
    private Double Precio;
    private String Imagen;
    private String Descripcion;

    public int getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public void setCantidad(int cantidad){this.Cantidad=cantidad;}

    public int getCantidad(){return this.Cantidad;}

}
