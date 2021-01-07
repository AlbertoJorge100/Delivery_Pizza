package com.example.proyecto_delivery.Interfaces;

import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalcularTotales {
    public static Logger logger = Logger.getInstance();
    public static List<Carrito> Lista=logger.getListaCarrito();
    public static int TotalProductos(){
        int cantidad=0;
        for(Carrito aux:Lista){
            cantidad+=aux.getCantidad();
        }
        return cantidad;
    }

    public static double TotalPagar(){
        double total=0;
        for(Carrito aux:Lista){
            total+=aux.getTotal();
        }
        BigDecimal bd = new BigDecimal(total);//Redondeamos a dos decimales
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
