package com.example.closhare.no_autorizado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.closhare.R;
import com.example.closhare.no_autorizado.Login;

public class Welcome extends AppCompatActivity {

//    Difinimos objetos de la vista
    Button empezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        Difiniendo objetos con id de los elementos
        empezar = findViewById(R.id.button_empezar);

//        onClicks
        empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}