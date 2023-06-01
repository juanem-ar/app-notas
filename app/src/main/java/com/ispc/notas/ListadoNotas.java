package com.ispc.notas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;

public class ListadoNotas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_notas);

        Button logoutButton = findViewById(R.id.logoutButton);
        Button crearNota = findViewById(R.id.crearNota);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        crearNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListadoNotas.this, NuevaNota.class);
                startActivity(intent);
            }
        });
    }
    private void logout() {
        WebAuthProvider.logout(new Auth0(this))
                .withScheme("demo")
                .start(this, new Callback<Void, AuthenticationException>() {
                    @Override
                    public void onSuccess(@Nullable Void payload) {
                        Intent intent = new Intent(ListadoNotas.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(ListadoNotas.this, "Gracias por visitarnos", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull AuthenticationException error) {
                        //Log out canceled, keep the user logged in
                        showNextActivity();
                    }
                });
    }
    private void showNextActivity() {
        Intent intent = new Intent(ListadoNotas.this, ListadoNotas.class);
        startActivity(intent);
        finish();
    }

}