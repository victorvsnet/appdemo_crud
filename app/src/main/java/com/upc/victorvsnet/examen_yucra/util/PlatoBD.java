package com.upc.victorvsnet.examen_yucra.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlatoBD extends SQLiteOpenHelper {

    public PlatoBD(Context context){super(context, Constantes.NOMBRE_BD, null, Constantes.VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + Constantes.NOMBRE_TABLA +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nombre TEXT NOT NULL, " +
                " descripcion TEXT NOT NULL, " +
                " precio REAL NOT NULL, " +
                " region INTEGER NOT NULL, " +
                " categoria INTEGER NOT NULL, " +
                " clasificacion INTEGER NOT NULL)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
