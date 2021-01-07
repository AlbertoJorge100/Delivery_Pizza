package com.example.proyecto_delivery.Utilerias;

import com.example.proyecto_delivery.Entidades.Carrito;

import java.util.ArrayList;
import java.util.List;

/***
 * Clase de Singleton
 */
public class Logger {
    private static Logger Log;

    private int IdUsuario;
    private String Nombres;
    private String Usuario;
    private String Password;
    private String Correo;
    private String Telefono;
    private String Direccion;
    private List<Carrito>ListaCarrito;

    //Variables para modificar la accion a realizar al ver el detalle de un producto
    public enum _Opcion {AGREGAR, MODIFICAR};
    private _Opcion Opcion=_Opcion.AGREGAR;

    private Logger(){
        this.ListaCarrito=new ArrayList<>();
    }
    //synchronized cuando se usan muchos hilos no hayan errores
    public static synchronized  Logger getInstance(){
        if(Log==null){
           Log=new Logger();
        }
        return Log;
    }
    public void setUsuario(String usuario){this.Usuario=usuario;}
    public String getUsuario(){return this.Usuario;}
    public void setIdUsuario(int IdUsuario){this.IdUsuario=IdUsuario;}
    public int getIdUsuario(){return this.IdUsuario;}
    public void setPassword(String password){this.Password=password;}
    public String getPassword(){return this.Password;}
    public void setCorreo(String correo){this.Correo=correo;}
    public String getCorreo(){return this.Correo;}
    public void setDireccion(String direccion){this.Direccion=direccion;}
    public String getDireccion(){return this.Direccion;}
    public void setTelefono(String telefono){this.Telefono=telefono;}
    public String getTelefono(){return this.Telefono;}
    public void setNombres(String nombres){this.Nombres=nombres;}
    public String getNombres(){return this.Nombres;}
    public void setItemCarrito(Carrito carrito){
        this.ListaCarrito.add(carrito);
    }
    public void setOpcion(_Opcion opcion){
        this.Opcion=opcion;
    }
    public _Opcion getOpcion(){
        return this.Opcion;
    }
    public List<Carrito> getListaCarrito(){return this.ListaCarrito;}
}
