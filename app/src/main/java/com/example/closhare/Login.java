package com.example.closhare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        volver = findViewById(R.id.login_entrar);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver.setText("Hola");
            }
        });



    }


}