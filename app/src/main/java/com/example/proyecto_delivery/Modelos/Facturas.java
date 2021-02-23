package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Clase para recibir la lista de facturas que un Usuario ha realizado,
 * y posterior a ello visualizar los productos dentro de la orden. utilizada en Historial de compras..
 */
public class Facturas implements Serializable {
    @SerializedName("IDFactura")
    private int IDFactura;
    @SerializedName("Fecha")
    private String Fecha;
    @SerializedName("Productos")
    private int Productos;
    @SerializedName("Total")
    private Double Total;
    @SerializedName("EstadoEnvio")
    private String EstadoEnvio;
    @SerializedName("Direccion")
    private String Direccion;
    @SerializedName("NumeroOrden")
    private String NumeroOrden;

    public int getIDFactura() {
        return IDFactura;
    }

    public void setIDFactura(int IDFactura) {
        this.IDFactura = IDFactura;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getProductos() {
        return Productos;
    }

    public void setProductos(int productos) {
        Productos = productos;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public String getEstadoEnvio() {
        return EstadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        EstadoEnvio = estadoEnvio;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getNumeroOrden() {
        return NumeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        NumeroOrden = numeroOrden;
    }
}
