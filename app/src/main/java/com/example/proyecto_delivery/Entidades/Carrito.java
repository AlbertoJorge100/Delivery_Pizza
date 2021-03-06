package com.example.proyecto_delivery.Entidades;

/**
 * Clase encargada de almacenar los productos en el carrito de compras, que posteriormente
 * seran enviados al servidor
 */
public class Carrito {
    private int idProducto;
    private String Producto;
    private double PrecioUnitario;
    private double Total;
    private int Cantidad;
    private int Cant_Limite;
    private String Imagen;
    private String Descripcion;
    public Carrito(){
        this.idProducto=0;
        this.Cant_Limite=0;
        this.Descripcion="";
    }

    public void setCant_Limite(int cant_Limite){this.Cant_Limite=cant_Limite;}

    public int getCant_Limite(){return this.Cant_Limite;}

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {Producto = producto;}

    public double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        PrecioUnitario = precioUnitario;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public void setImagen(String Imagen){this.Imagen=Imagen; }

    public String getImagen(){
        return this.Imagen;
    }

    public void setDescripcion(String descripcion){this.Descripcion=descripcion;}

    public String getDescripcion(){return this.Descripcion;}
}
