package com.example.proyecto_delivery.Modelos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuarios implements Serializable {
    @SerializedName("IDUsuario")
    private int IDUsuario;
    @SerializedName("Nombres")
    private String Nombres;
    @SerializedName("Correo")
    private String Correo;
    @SerializedName("Telefono")
    private String Telefono;
    @SerializedName("Usuario")
    private String Usuario;
    @SerializedName("Password")
    private String Password;
    @SerializedName("Compras")
    private int Compras;
    public Usuarios(){
        this.Compras=0;
        this.Nombres="";
        this.Correo="";
        this.Telefono="";
        this.Usuario="";
        this.Password="";
    }

    public int getCompras(){return this.Compras;}

    public void setCompras(int compras){this.Compras=compras;}

    public int getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
