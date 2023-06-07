package com.ispc.notas.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notas.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "notas" si no existe
        String createTableQuery = "CREATE TABLE IF NOT EXISTS notas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "descripcion TEXT" +
                ")";
        db.execSQL(createTableQuery);
        // TODO gestionar la/s tablas necesarias para almacenar los usuarios registrados
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Para migrar si fuera necesario
    }

}
