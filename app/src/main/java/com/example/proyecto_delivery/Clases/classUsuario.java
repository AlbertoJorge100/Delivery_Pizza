package com.example.proyecto_delivery.Clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.example.proyecto_delivery.BaseDatos.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class classUsuario extends Persistencia {
    private String Nombres;
    private int IdUsuario;
    private String Telefono;
    private String Correo;
    private String Direccion;
    private String Usuario;
    private String Password;
    public static final String TABLA_USUARIOS = "Usuarios";
    public static final String ID_USUARIO = "IdUsuario";
    public static final String NOMBRE = "Nombres";
    public static final String TELEFONO = "Telefono";
    public static final String CORREO = "Correo";
    public static final String DIRECCION = "Direccion";
    public static final String USUARIO = "Usuario";
    public static final String PASSWORD = "Password";

    public static final String QUERY_CREATE_TABLE =
            "CREATE TABLE "+TABLA_USUARIOS+" (" +ID_USUARIO+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOMBRE+" TEXT NOT NULL, " +
                    TELEFONO+" TEXT NOT NULL, " +
                    CORREO+" TEXT NOT NULL, " +
                    DIRECCION+" TEXT NOT NULL, " +
                    USUARIO+" TEXT NOT NULL, " +
                    PASSWORD+" TEXT NOT NULL); ";

    public static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS "+TABLA_USUARIOS;
    private static final String[] FIELDS = new String[]{ID_USUARIO, NOMBRE,TELEFONO,CORREO,DIRECCION,USUARIO,PASSWORD};

    public classUsuario(Context contexto) {
        super(contexto);
    }

    public classUsuario(){
        super(null);
    }
    @Override
    public boolean Insert() throws SQLiteException {
        boolean valReturn = false;
        //Creamos el Content Values para Libros
        ContentValues valoresInsert = new ContentValues();
        valoresInsert.put(FIELDS[1], this.Nombres);
        valoresInsert.put(FIELDS[2], this.Telefono);
        valoresInsert.put(FIELDS[3], this.Correo);
        valoresInsert.put(FIELDS[4], this.Direccion);
        valoresInsert.put(FIELDS[5], this.Usuario);
        valoresInsert.put(FIELDS[6], this.Password);
        //Ejecutamos la consulta de la insercion
        super.Abrir();
        if(super.getDataBase().insert(TABLA_USUARIOS, null, valoresInsert) > 0){
            valReturn = true;
        }
        super.Cerrar();
        return valReturn;
    }

    @Override
    public boolean Update() throws SQLiteException {
        boolean valReturn = false;
        ContentValues valoresUpdate = new ContentValues();
        valoresUpdate.put(FIELDS[1], this.Nombres);
        valoresUpdate.put(FIELDS[2], this.Telefono);
        valoresUpdate.put(FIELDS[3], this.Correo);
        valoresUpdate.put(FIELDS[4], this.Direccion);
        valoresUpdate.put(FIELDS[5], this.Usuario);
        valoresUpdate.put(FIELDS[6], this.Password);
        super.Abrir();
        if(super.getDataBase().update(TABLA_USUARIOS, valoresUpdate, FIELDS[0] +" = " + Integer.toString(this.IdUsuario),null) > 0 ){
            valReturn = true;
        }
        super.Cerrar();
        return valReturn;
    }

    public List<classUsuario> GetAllUsuarios(){
        List<classUsuario> listUsuario = new ArrayList<>();
        super.Abrir();
        Cursor listReturn = this.GetAll();
        //Validamos que exista información en el cursor
        if(listReturn != null && listReturn.getCount() > 0){
            //Moverse al primer elemento
            listReturn.moveToFirst();
            do{
                //Creamos el libro que se incluira en la lista
                classUsuario nuevoUsuario = new classUsuario();
                nuevoUsuario.setIdUsuario(listReturn.getInt(0));
                nuevoUsuario.setNombres(listReturn.getString(1));
                nuevoUsuario.setTelefono(listReturn.getString(2));
                nuevoUsuario.setCorreo(listReturn.getString(3));
                nuevoUsuario.setDireccion(listReturn.getString(4));
                nuevoUsuario.setUsuario(listReturn.getString(5));
                nuevoUsuario.setPassword(listReturn.getString(6));
                //Agregamos a la lista
                listUsuario.add(nuevoUsuario);
            }while (listReturn.moveToNext());
        }
        super.Cerrar();
        return listUsuario;
    }
    /*
    public boolean GetUsuario(int IdUsuario){
        boolean valReturn = false;
        super.Abrir();//Pendiente de revision
        Cursor usuarioEncontrado = super.getDataBase().query(TABLA_USUARIOS,FIELDS,ID_USUARIO +" = "+ Integer.toString(IdUsuario),null,null,null,null);
        //Validamos que exista información en el cursor
        if(usuarioEncontrado != null && usuarioEncontrado.getCount() == 1){
            usuarioEncontrado.moveToFirst();
            this.setIdUsuario(usuarioEncontrado.getInt(0));
            this.setNombres(usuarioEncontrado.getString(1));
            this.setTelefono(usuarioEncontrado.getString(2));
            this.setCorreo(usuarioEncontrado.getString(3));
            this.setDireccion(usuarioEncontrado.getString(4));
            this.setUsuario(usuarioEncontrado.getString(5));
            this.setPassword(usuarioEncontrado.getString(6));
            valReturn = true;
        }
        super.Cerrar();
        return valReturn;
    }
    */
    public boolean GetUsuario(String usuario){
        boolean valReturn = false;
        super.Abrir();//Pendiente de revision
        Cursor usuarioEncontrado = super.getDataBase().query(TABLA_USUARIOS,FIELDS,USUARIO +" = "+"'"+usuario+"'",null,null,null,null);
        //Validamos que exista información en el cursor
        if(usuarioEncontrado != null && usuarioEncontrado.getCount() == 1){
            usuarioEncontrado.moveToFirst();
            this.setIdUsuario(usuarioEncontrado.getInt(0));
            this.setNombres(usuarioEncontrado.getString(1));
            this.setTelefono(usuarioEncontrado.getString(2));
            this.setCorreo(usuarioEncontrado.getString(3));
            this.setDireccion(usuarioEncontrado.getString(4));
            this.setUsuario(usuarioEncontrado.getString(5));
            this.setPassword(usuarioEncontrado.getString(6));
            valReturn = true;
        }
        super.Cerrar();
        return valReturn;
    }
    @Override
    public boolean Delete() {
        boolean valReturn = false;
        super.Abrir();
        if(super.getDataBase().delete(TABLA_USUARIOS,FIELDS[0] + "=" + Integer.toString(this.IdUsuario), null ) > 0 ){
            valReturn = true;
        }
        super.Cerrar();
        return valReturn;
    }
    @Override
    protected Cursor GetAll() {
        Cursor listReturn = null;
        listReturn = super.getDataBase().query(TABLA_USUARIOS, FIELDS,null,null,null,null,null);
        return listReturn;
    }

    public String getNombres() {
        return Nombres;
    }
    public void setNombres(String login) {
        this.Nombres = login;
    }
    public int getIdUsuario() {
        return this.IdUsuario;
    }
    public void setIdUsuario(int id) {
        this.IdUsuario = id;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
    public String getCorreo() {
        return Correo;
    }
    public void setCorreo(String correo) {
        Correo = correo;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String direccion) {
        Direccion = direccion;
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
