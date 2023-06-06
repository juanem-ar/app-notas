package com.ispc.notas.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ispc.notas.R;
import com.ispc.notas.utils.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ListadoNotasActivity extends AppCompatActivity {

    private ListView listViewNotas;
    private ArrayAdapter<String> adapter;
    private List<String> notasList;
    private Button crearNotaButton;
    private Button logoutButton;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_notas);

        listViewNotas = findViewById(R.id.listViewNotas);
        crearNotaButton = findViewById(R.id.crearNota);
        logoutButton = findViewById(R.id.logoutButton);

        notasList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notasList);
        listViewNotas.setAdapter(adapter);

        // Obtén una referencia a la base de datos
        dbHelper = new DbHelper(this);

        crearNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCrearNota();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualiza la lista de notas cuando la actividad vuelve a estar en primer plano
        actualizarListaNotas();
    }

    private void actualizarListaNotas() {
        // Limpia la lista actual antes de agregar las nuevas notas
        notasList.clear();

        // Obtén una referencia a la base de datos en modo lectura
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // Consulta las notas de la tabla "notas"
        String[] projection = {"titulo", "descripcion"};
        Cursor cursor = database.query("notas", projection, null, null, null, null, null);

        // Leer las notas del cursor y agregarlas a la lista
        while (cursor.moveToNext()) {
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
            String nota = titulo + "\n " + descripcion;
            notasList.add(nota);
        }

        // Cerrar el cursor y liberar recursos
        cursor.close();
        database.close();

        // Notificar al adaptador que los datos han cambiado
        adapter.notifyDataSetChanged();
    }

    private void abrirCrearNota() {
        Intent intent = new Intent(this, NuevaNotaActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        //TODO Realizar las acciones necesarias para cerrar sesión
        // Por ejemplo, borrar el token de autenticación o limpiar los datos de usuario

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
