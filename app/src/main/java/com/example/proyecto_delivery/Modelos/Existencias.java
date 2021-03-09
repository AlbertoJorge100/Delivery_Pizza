package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Existencias implements Serializable {
    @SerializedName("id")
    private int id;//Id del producto
    @SerializedName("existencias")
    private int existencias;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }
}
