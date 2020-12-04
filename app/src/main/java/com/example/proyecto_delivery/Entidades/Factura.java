package com.example.proyecto_delivery.Entidades;

public class Factura {
    private int IdFactura;
    private String Fecha;
    private int Cantidad;
    private Double Total;
    private String Imagen;

    public Factura(){
        this.Imagen="https://image.freepik.com/vector-gratis/pago-online-smartphone_23-2147678507.jpg";
    }
    public int getIdFactura() {
        return IdFactura;
    }

    public void setIdFactura(int idFactura) {
        IdFactura = idFactura;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
