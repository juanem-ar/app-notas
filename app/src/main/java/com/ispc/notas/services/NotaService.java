package com.ispc.notas.services;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ispc.notas.models.Nota;
import com.ispc.notas.utils.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class NotaService {
    private final SQLiteDatabase database;

    public NotaService(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Nota createNota(String title, String description) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);

        long id = database.insert("notes", null, values);

        Nota nota = new Nota();
        nota.setId(id);
        nota.setTitle(title);
        nota.setDescription(description);
        return nota;
    }

    public List<Nota> getNotas() {
        List<Nota> notas = new ArrayList<>();

        Cursor cursor = database.query("notes", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));

                Nota nota = new Nota();
                nota.setId(id);
                nota.setTitle(title);
                nota.setDescription(description);

                notas.add(nota);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return notas;
    }

    // Rest of the methods for updating and deleting notes

    public void closeConnection() {
        database.close();
    }
}
