package com.example.proyecto_delivery.Modelos;

/**
 * -- Al presionar una factura especifica, en la lista de facturas realizadas.
 * Clase para recibir desde el servidor web, los productos en una factura especifica,
 * que un usuario haya gestionado
 */
public class OrdenProductos {
    private String Imagen;
    private String Producto;
    private Double Precio;
    private int Cantidad;
    private Double SubTotal;

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
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

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(Double subTotal) {
        SubTotal = subTotal;
    }
}
