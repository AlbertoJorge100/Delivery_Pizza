package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * -- Al presionar pagar en la activity Pagos.
 * Clase encargada de enviar los datos requeridos de cada producto, hacia el
 * servidor web.
 */
public class FacturaProductos implements Serializable {
    @SerializedName("IDFacturaProducto")
    private int IDFacturaProducto;
    @SerializedName("IDProducto")
    private int IDProducto;
    @SerializedName("IDFactura")
    private int IDFactura;
    @SerializedName("Cantidad")
    private int Cantidad;
    @SerializedName("Descuento")
    private Double Descuento;
    @SerializedName("SubTotal")
    private Double SubTotal;

    public int getIDFacturaProducto() {
        return IDFacturaProducto;
    }

    public void setIDFacturaProducto(int IDFacturaProducto) {
        this.IDFacturaProducto = IDFacturaProducto;
    }

    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public int getIDFactura() {
        return IDFactura;
    }

    public void setIDFactura(int IDFactura) {
        this.IDFactura = IDFactura;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Double getDescuento() {
        return Descuento;
    }

    public void setDescuento(Double descuento) {
        Descuento = descuento;
    }

    public Double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(Double subTotal) {
        SubTotal = subTotal;
    }
}
