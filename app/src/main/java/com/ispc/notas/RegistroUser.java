package com.ispc.notas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistroUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_user);

        Button crearCuenta = findViewById(R.id.crearCuentaBoton);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView repassword = (TextView) findViewById(R.id.repassword);

        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !repassword.getText().toString().isEmpty()){
                    if(validarEmail(username.getText().toString())){
                        if(password.getText().toString().equals(repassword.getText().toString())){
                            // TODO logica del register
                            Intent intent = new Intent(RegistroUser.this, ListadoNotas.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegistroUser.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegistroUser.this, "El email tiene un formato incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroUser.this, "Faltan ingresar datos", Toast.LENGTH_SHORT).show();
                }

                //TODO logica de registro
            }
        });
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}