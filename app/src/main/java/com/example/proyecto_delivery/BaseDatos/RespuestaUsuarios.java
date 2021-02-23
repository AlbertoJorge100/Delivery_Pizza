package com.example.proyecto_delivery.BaseDatos;

import com.example.proyecto_delivery.Modelos.Categorias;
import com.example.proyecto_delivery.Modelos.Usuarios;

import java.util.List;
/*Clase para Mantenimiento de Usuario, en el servidor*/
public class RespuestaUsuarios {
    private Usuarios usuario;
    private String mensaje;
    private String codigo;
    private List<Categorias> categorias;
    public Usuarios getUsuario(){return this.usuario;}
    public void setUsuario(Usuarios usuario){this.usuario=usuario;}
    public void setMensaje(String mensaje){this.mensaje=mensaje;}
    public String getMensaje(){return this.mensaje;}
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public List<Categorias> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<Categorias> categorias) {
        this.categorias = categorias;
    }
}
