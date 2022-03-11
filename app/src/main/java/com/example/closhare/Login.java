package com.example.closhare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {


//    Definiendo objetos de la vista
    Button entrar;
    TextView crear_cuenta, restaurar_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Difiniendo objetos con id de los elementos
        entrar = findViewById(R.id.login_entrar);
        crear_cuenta = findViewById(R.id.crear_cuenta);
        restaurar_pass = findViewById(R.id.restaurar_pass);

//        onClicks
        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registrar.class));
            }
        });

        restaurar_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RestaurarPass.class));
            }
        });

    }
}