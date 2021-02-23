package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pagos implements Serializable {
    @SerializedName("Direccion")
    private String Direccion;
    @SerializedName("ListaProductos")
    private List<FacturaProductos> ListaProductos;

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
}
