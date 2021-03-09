package com.example.proyecto_delivery.BaseDatos;

import com.example.proyecto_delivery.Modelos.Existencias;

import java.util.List;

/**
 * Clase para conectar al web services de los productos
 */
public class RespuestaProductos {
    private String mensaje;
    private String codigo;
    private List<Existencias> data;

    public String getMensaje(){return this.mensaje;}
    public String getCodigo(){return this.codigo;}
    public List<Existencias> getData(){return data;}

    public void setMensaje(String mensaje){this.mensaje=mensaje;}
    public void setCodigo(String codigo){this.codigo=codigo;}
    public void setData(List<Existencias> data){this.data=data;}
}
