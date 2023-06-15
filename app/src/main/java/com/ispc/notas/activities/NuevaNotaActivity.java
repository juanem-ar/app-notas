package com.ispc.notas.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ispc.notas.R;
import com.ispc.notas.models.Nota;
import com.ispc.notas.services.NotaService;

public class NuevaNotaActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private Button btnGuardar;

    private NotaService notaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);

        // Inicializar NotaService
        notaService = new NotaService(this);

        // Obtener referencias de vistas
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Configurar el click listener para el botón Guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNota();
            }
        });
    }

    private void guardarNota() {
        // Obtener los valores de los campos de texto
        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();

        // Validar que los campos no estén vacíos
        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la nueva nota usando el servicio
        Nota nota = notaService.createNota(titulo, descripcion);

        // Mostrar un mensaje de éxito y finalizar la actividad
        Toast.makeText(this, "Nota guardada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la conexión del servicio al destruir la actividad
        notaService.closeConnection();
    }
}
