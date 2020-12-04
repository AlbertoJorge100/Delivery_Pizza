package com.example.proyecto_delivery.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.proyecto_delivery.BaseDatos.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class classFactura extends Persistencia {
    private int IdFactura;
    private String Fecha;
    private int CantidadProductos;
    private Double Total;
    private int IdCliente;


    public static final String TABLA_FACTURAS = "Facturas";
    public static final String ID_FACTURA = "IdFactura";
    public static final String FECHA = "Fecha";
    public static final String CANT_PROD = "cantidad_productos";
    public static final String TOTAL = "Total";
    public static final String ID_CLIENTE = "IdCliente";



    public static final String QUERY_CREATE_TABLE =
            "CREATE TABLE "+TABLA_FACTURAS+" (" +ID_FACTURA+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FECHA+" TEXT NOT NULL, " +
                    CANT_PROD+" INTEGER NOT NULL, " +
                    TOTAL+" DECIMAL NOT NULL, " +
                    ID_CLIENTE+" INTEGER NOT NULL) ";

    public static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS "+TABLA_FACTURAS;
    private static final String[] FIELDS = new String[]{ID_FACTURA,FECHA,CANT_PROD,TOTAL,ID_CLIENTE};



    public classFactura(Context contexto){
        super(contexto);
    }
    public classFactura(){
        super(null);
    }

    @Override
    public boolean Insert() {
        boolean resultado=false;
        int maxid=-1;
        //Creamos el Content Values para Libros
        ContentValues valoresInsert = new ContentValues();
        valoresInsert.put(FIELDS[1], this.Fecha);
        valoresInsert.put(FIELDS[2], Integer.toString(this.CantidadProductos));
        valoresInsert.put(FIELDS[3], Double.toString(this.Total));
        valoresInsert.put(FIELDS[4], Integer.toString(this.IdCliente));
        //Ejecutamos la consulta de la insercion
        super.Abrir();
        if(super.getDataBase().insert(TABLA_FACTURAS, null, valoresInsert) > 0){
            String selectQuery = "SELECT max("+ID_FACTURA+") as id FROM "+TABLA_FACTURAS;
            Cursor cursor = super.getDataBase().rawQuery(selectQuery, null);
            cursor.moveToFirst();
            maxid = cursor.getInt(cursor.getColumnIndex("id"));
            this.setIdFactura(maxid);
            resultado=true;
        }
        super.Cerrar();
        return resultado;
    }


    public List<classFactura> GetAllFacturas(){
        List<classFactura> listProducto = new ArrayList<>();
        super.Abrir();
        Cursor listReturn = this.GetAll();
        //Validamos que exista información en el cursor
        if(listReturn != null && listReturn.getCount() > 0){
            //Moverse al primer elemento
            listReturn.moveToFirst();
            do{
                //Creamos el libro que se incluira en la lista
                classFactura nuevoProducto = new classFactura();
                nuevoProducto.setIdFactura(listReturn.getInt(0));
                nuevoProducto.setFecha(listReturn.getString(1));
                nuevoProducto.setCantidadProductos(listReturn.getInt(2));
                nuevoProducto.setTotal(listReturn.getDouble(3));
                nuevoProducto.setIdCliente(listReturn.getInt(4));
                //Agregamos a la lista
                listProducto.add(nuevoProducto);
            }while (listReturn.moveToNext());
        }
        super.Cerrar();
        return listProducto;
    }

    @Override
    public boolean Update() {
        return false;
    }

    @Override
    public boolean Delete() {
        return false;
    }

    @Override
    protected Cursor GetAll() {
        Cursor listReturn = null;
        listReturn = super.getDataBase().query(TABLA_FACTURAS, FIELDS,ID_CLIENTE+" = "+Integer.toString(this.IdCliente),null,null,null,null);
        return listReturn;
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

    public int getCantidadProductos() {
        return CantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        CantidadProductos = cantidadProductos;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }
}
