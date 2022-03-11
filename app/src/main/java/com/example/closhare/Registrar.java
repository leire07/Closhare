package com.example.closhare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {


//    Definiendo objetos de la vista
    Button registrar;
    TextView iniciar_sesion, usuario_existe, pass_no_coinciden, no_rellenado1, no_rellenado2, no_rellenado3;
    TextInputEditText usuario,password,repit_pass;

//    Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser usuarioo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Difiniendo objetos con id de los elementos
        registrar = findViewById(R.id.button_register);
        iniciar_sesion = findViewById(R.id.register_iniciar_sesion);
        usuario_existe = findViewById(R.id.usuario_existe);
        pass_no_coinciden = findViewById(R.id.pass_diferentes);
        usuario = findViewById(R.id.register_username);
        password = findViewById(R.id.register_pass);
        repit_pass = findViewById(R.id.register_pass_repit);
        no_rellenado1 = findViewById(R.id.no_rellenado1);
        no_rellenado2 = findViewById(R.id.no_rellenado2);
        no_rellenado3 = findViewById(R.id.no_rellenado3);


//        Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usuarioo = FirebaseAuth.getInstance().getCurrentUser();

//        Ponemos textos rojos en invisible para luego si algo esta mal cambiarles a visible
        usuario_existe.setVisibility(View.INVISIBLE);
        pass_no_coinciden.setVisibility(View.INVISIBLE);
        no_rellenado1.setVisibility(View.INVISIBLE);
        no_rellenado2.setVisibility(View.INVISIBLE);
        no_rellenado3.setVisibility(View.INVISIBLE);

//        Al pinchar accede a bd
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Ver si los campos estan rellenados
                if(!usuario.getText().toString().isEmpty() || !password.getText().toString().isEmpty() || !repit_pass.getText().toString().isEmpty()){

//                    Primero comprobamos si las contrasenyas son iguales. Si no< mostramos pass_no_coinciden
                    if(!password.getText().equals(repit_pass.getText())){
                        pass_no_coinciden.setVisibility(View.VISIBLE);
                    }else{
//                    Conectamos con bd y perimero comprobamos si ya existe el usuario
                        registerUser();
                    }
                } else {
                    no_rellenado1.setVisibility(View.VISIBLE);
                    no_rellenado2.setVisibility(View.VISIBLE);
                    no_rellenado3.setVisibility(View.VISIBLE);

//                    Mostramos que los campos estan vacios
                    if(!usuario.getText().toString().isEmpty()){
                        no_rellenado2.setVisibility(View.INVISIBLE);
                    }
                    if(!password.getText().toString().isEmpty()){
                        no_rellenado1.setVisibility(View.INVISIBLE);
                    }
                    if(repit_pass.getText().toString().isEmpty()){
                        no_rellenado3.setVisibility(View.INVISIBLE);
                    }
                }
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

    private void registerUser(){

//        Guardamos en variables los valores de los campos
        String user = usuario.getText().toString();
        String pass = password.getText().toString();

//        Conectamos con la base de datos
        mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

//                     Creamos ek
                    Map<String, Object> user = new HashMap<>();
                    user.put("Email", user);
                    user.put("Contrasena", pass);

//                    Enviamos correo de verificacion al usuario
                    login();

                    db.collection("Users")
                            .document(usuarioo.getUid())
                            .set(user);
                    Log.d("Demo","El usuario ha sido registrado "+usuarioo.getEmail());
                    startActivity(new Intent(getApplicationContext(), Login.class));

                }else{
                    Log.d("Demo","El usuario no se pudo registrarse " + usuarioo.getEmail());
                    Toast.makeText(getApplicationContext(),"No se pudo registrar el usuario "
                            +task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void login() {
        usuarioo = FirebaseAuth.getInstance().getCurrentUser();
        usuarioo.sendEmailVerification();
        Log.d("Demo", "El correo ha sido enviado");
        Toast.makeText(this, "Se ha enviado un correo de confirmación", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, Login.class);
//        startActivity(intent);
    }
}