package com.example.proyecto_delivery.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.proyecto_delivery.Clases.classUsuario;

/**
 * Clase que permite la Administración del Archivo de Base de Datos
 */
public class HelperSQLite extends SQLiteOpenHelper {
    public HelperSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Permite la creación de la base de datos->[Tablas]
        //sqLiteDatabase.execSQL(classLibros.QUERY_CREATE_TABLE);
        sqLiteDatabase.execSQL(classUsuario.QUERY_CREATE_TABLE);
        //.......
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int odlVersion, int newVersion) {
        //Permite la actualización de la base de datos--->[Tablas]
        //sqLiteDatabase.execSQL(classLibros.QUERY_DROP_TABLE);
        sqLiteDatabase.execSQL(classUsuario.QUERY_DROP_TABLE);
        //.....
        onCreate(sqLiteDatabase);
    }
}
