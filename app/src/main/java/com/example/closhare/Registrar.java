package com.example.closhare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Registrar extends AppCompatActivity {


//    Definiendo objetos de la vista
    Button registrar;
    TextView iniciar_sesion, usuario_existe, pass_no_coinciden;

//    Firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Difiniendo objetos con id de los elementos
        registrar = findViewById(R.id.button_register);
        iniciar_sesion = findViewById(R.id.register_iniciar_sesion);
        usuario_existe = findViewById(R.id.usuario_existe);
        pass_no_coinciden = findViewById(R.id.pass_diferentes);

//        Firebase
        mAuth = FirebaseAuth.getInstance();

//        Ponemos textos rojos en invisible para luego si algo esta mal cambiarles a visible
        usuario_existe.setVisibility(View.INVISIBLE);
        pass_no_coinciden.setVisibility(View.INVISIBLE);

//        Al pinchar accede a bd
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        Al pinchar "iniciar sesion" nos lleva a Login.java
        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}