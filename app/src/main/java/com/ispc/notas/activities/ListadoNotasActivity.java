package com.ispc.notas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ispc.notas.R;
import com.ispc.notas.adapters.NotaAdapter;
import com.ispc.notas.models.Nota;
import com.ispc.notas.services.NotaService;
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
    private NotaAdapter notaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_notas);

        listViewNotas = findViewById(R.id.listViewNotas);
        crearNotaButton = findViewById(R.id.crearNota);
        logoutButton = findViewById(R.id.logoutButton);

        notasList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item_nota, R.id.tituloTextView, notasList);
        listViewNotas.setAdapter(adapter);

        dbHelper = new DbHelper(this);

        notaAdapter = new NotaAdapter(this, new ArrayList<Nota>());
        listViewNotas.setAdapter(notaAdapter);

        listViewNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Nota nota = notaAdapter.getItem(position);
                eliminarNota(nota);
            }
        });

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
        actualizarListaNotas();
    }

    private void actualizarListaNotas() {
        NotaService notaService = new NotaService(this);
        List<Nota> notas = notaService.getNotas();

        notaAdapter.clear();
        notaAdapter.addAll(notas);
        notaAdapter.notifyDataSetChanged();
    }

    private void eliminarNota(Nota nota) {
        NotaService.deleteNota(dbHelper.getWritableDatabase(), nota.getId());
        actualizarListaNotas();
    }

    private void abrirCrearNota() {
        Intent intent = new Intent(this, NuevaNotaActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
