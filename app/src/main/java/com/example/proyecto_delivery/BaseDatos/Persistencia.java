package com.example.proyecto_delivery.BaseDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Permite crear el objeto de Base de Datos con la ayuda del Helper
 */
public abstract class Persistencia {
    private static final String DATABASE = "db_delivery.db";
    private static final int VERSION = 1;
    private SQLiteDatabase db;
    private HelperSQLite helpDB;


    /**
     * Lectura de Base de Datos
     * @return
     */
    public SQLiteDatabase getDataBase(){
        return this.db;
    }

    /**
     * Permite inicializar la administración de la base de datos
     * @param contexto
     */
    public Persistencia(Context contexto){
        this.helpDB = new HelperSQLite(contexto,DATABASE,null, VERSION);
    }

    /**
     * Permite abrir la base de datos para la ejecución de consultas
     * @return
     */
    protected boolean Abrir() throws SQLiteException {
        this.db = this.helpDB.getWritableDatabase();
        return true;
    }

    /**
     * Permite cerrar el archivo de base de datos
     * @return
     * @throws SQLiteException
     */
    protected boolean Cerrar() throws SQLiteException{
        this.db.close();
        return true;
    }

    //Definición de las funciones de manejo de tablas
    public abstract boolean Insert();
    public abstract boolean Update();
    public abstract boolean Delete();
    //Select protegidos
    protected abstract Cursor GetAll();

}
