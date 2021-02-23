package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Clase para gestionar una nueva orden y una nueva factura
 */
public class IDFactura implements Serializable {
    @SerializedName("IDFactura")
    private int IDFactura;
    @SerializedName("NumeroOrden")
    private int NumeroOrden;

    public int getIDFactura() {
        return IDFactura;
    }

    public void setIDFactura(int IDFactura) {
        this.IDFactura = IDFactura;
    }

    public int getNumeroOrden() {
        return NumeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        NumeroOrden = numeroOrden;
    }
}
