package com.example.closhare.no_autorizado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.closhare.R;
import com.example.closhare.no_autorizado.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RestaurarPass extends AppCompatActivity {

//    Definiendo objetos de la vista
    Button enviar;
    TextInputEditText restaurar;


//    Firebase
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurar_pass);

//        Difiniendo objetos con id de los elementos
        restaurar = findViewById(R.id.restaurar_email);
        enviar = findViewById(R.id.button_enviar_correo);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");

//        onClicks
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Asignamos valor del campo a un string
                String correo = restaurar.getText().toString();

                if(!correo.isEmpty()){
//                    Comprobando que correo sea de forma correcta
                    if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                        Toast.makeText(getApplicationContext(), "Ingresa el correo válido", Toast.LENGTH_SHORT).show();
                    } else {
//                        Metodo para enviar el correo al usuario
                        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Correo ha sido enviado correctamente", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "No existe usuario con este correo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Rellena el campo, por favor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
