package com.example.proyecto_delivery.BaseDatos;

/**
 * Clase para conectar al web services de los productos
 */
public class RespuestaProductos {
    private String mensaje;
    private Boolean data;
    public Boolean getData(){return data;}
    public void setFacturaProductos(Boolean facturaProductos){this.data=facturaProductos;}
    public String getMensaje(){return this.mensaje;}
    public void setMensaje(String mensaje){this.mensaje=mensaje;}
}
