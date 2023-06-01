package com.ispc.notas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.Callback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Auth0 auth0;

    public static final String EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        TextView registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistroUser.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        auth0 = new Auth0(this);

        //Check if the activity was launched to log the user out
        if (getIntent().getBooleanExtra(EXTRA_CLEAR_CREDENTIALS, false)) {
            logout();
        }
    }
    private void login() {
        WebAuthProvider.login(auth0)
                .withScheme("demo")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .start(this, new Callback<Credentials, AuthenticationException>() {

                    @Override
                    public void onFailure(@NonNull final AuthenticationException exception) {
                        Toast.makeText(MainActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(@Nullable final Credentials credentials) {
                        Intent intent = new Intent(MainActivity.this, ListadoNotas.class);
                        intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.getAccessToken());
                        startActivity(intent);
                        finish();
                    }
                });
    }
    private void logout() {
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(this, new Callback<Void, AuthenticationException>() {
                    @Override
                    public void onSuccess(@Nullable Void payload) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
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
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}