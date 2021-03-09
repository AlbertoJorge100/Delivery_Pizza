package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Clase que envia los datos, de la compra: lista de productos, datos de envio, datos de tarjeta de credito.
 */
public class Pagos implements Serializable {
    @SerializedName("Direccion")
    private String Direccion;
    @SerializedName("ListaProductos")
    private List<FacturaProductos> ListaProductos;
    @SerializedName("Tarjeta")
    private String Tarjeta;
    @SerializedName("CVV")
    private String CVV;
    @SerializedName("YY")
    private String YY;
    @SerializedName("MM")
    private String MM;
    @SerializedName("Propietario")
    private String Propietario;

    public String getPropietario(){return this.Propietario;}

    public void setPropietario(String propietario){this.Propietario=propietario;}

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public List<FacturaProductos> getListaProductos() {
        return ListaProductos;
    }

    public void setListaProductos(List<FacturaProductos> listaProductos) {
        this.ListaProductos = listaProductos;
    }

    public String getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        Tarjeta = tarjeta;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getYY() {
        return YY;
    }

    public void setYY(String YY) {
        this.YY = YY;
    }

    public String getMM() {
        return MM;
    }

    public void setMM(String MM) {
        this.MM = MM;
    }
}
