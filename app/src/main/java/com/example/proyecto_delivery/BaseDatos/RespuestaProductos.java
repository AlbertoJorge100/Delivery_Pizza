package com.example.proyecto_delivery.BaseDatos;

import com.example.proyecto_delivery.Modelos.Existencias;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para conectar al web services de los productos
 */
public class RespuestaProductos implements Serializable {
    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("codigo")
    private String codigo;
    @SerializedName("data")
    private List<Existencias> data;
    public RespuestaProductos(){
        this.mensaje="";
        this.codigo="";
        this.data=new ArrayList<>();
    }
    public String getMensaje(){return this.mensaje;}
    public String getCodigo(){return this.codigo;}
    public List<Existencias> getData(){return data;}

    public void setMensaje(String mensaje){this.mensaje=mensaje;}
    public void setCodigo(String codigo){this.codigo=codigo;}
    public void setData(List<Existencias> data){this.data=data;}
}
