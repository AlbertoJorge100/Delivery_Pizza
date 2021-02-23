package com.example.proyecto_delivery.Utilerias;

import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Modelos.Usuarios;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/***
 * Clase de Singleton
 */
public class Logger {
    private static Logger Log;
    private Usuarios Usuario;
    private List<Carrito>ListaCarrito;
    private String Password2;
    //Variables para modificar la accion a realizar al ver el detalle de un producto
    public enum _Opcion {AGREGAR, MODIFICAR};
    private _Opcion Opcion=_Opcion.AGREGAR;
    private int IDFactura;
    private int NumeroOrden;
    private Boolean Gestionado;

    private Logger(){
        this.Gestionado=false;
        this.ListaCarrito=new ArrayList<>();
    }
    //synchronized cuando se usan muchos hilos no hayan errores
    public static synchronized  Logger getInstance(){
        if(Log==null){
           Log=new Logger();
        }
        return Log;
    }

    public void setUsuario(Usuarios usuario){
        this.Usuario=usuario;
    }
    public Usuarios getUsuario(){
        return this.Usuario;
    }
    public String getTotalPagar(){
        double resultado=0;
        for(Carrito aux:this.ListaCarrito){
            resultado+=aux.getTotal();
        }

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(resultado).replace(",", ".");
    }
    public int getCantidadProductos(){
        int i=0;
        for(Carrito aux:this.ListaCarrito){
            i+=aux.getCantidad();
        }
        return i;
    }
    public int getItems(){
        return this.ListaCarrito.size();
    }
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
    public void setPassword2(String password2){
        int cont=password2.length();
        String pal="";
        for(int i=0;i<cont;i++){
            pal+="*";
        }
        this.Password2=pal;
    }
    public String getPassword2(){return this.Password2;}
    public void setIDFactura(int idFactura){
        this.Gestionado=true;
        this.IDFactura=idFactura;
    }
    public void setNumeroOrden(int numeroOrden){this.NumeroOrden=numeroOrden;}
    public int getIDFactura(){return this.IDFactura;}
    public int getNumeroOrden(){return this.NumeroOrden;}
    public void setGestionado(Boolean gestionado){this.Gestionado=gestionado;}
    public Boolean getGestionado(){return this.Gestionado;}
}
