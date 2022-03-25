package com.example.closhare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PruebaArmario extends AppCompatActivity {

    RecyclerView recyclerViewArmario;
    PrendasListAdaptador adaptador;
//    TODO poder fotos en mapa
    List<HashMap> leer = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    Button casa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_armario);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        casa = findViewById(R.id.a_home);
        casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PruebaHome.class));
            }
        });


        db.collection("Armario")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    for(int i=0; i<task.getResult().getData().size(); i++){
                        leer.add(i, (HashMap) task.getResult().getData().get("Foto"+i));
                    }

                    Log.d("Leer", leer.toString());

                    recyclerViewArmario = findViewById(R.id.recyclerViewArmario);
//        La captura de como rellenarlo completo esta en es escritorio.
                    recyclerViewArmario.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    adaptador = new PrendasListAdaptador(getApplicationContext(), leer);
                    recyclerViewArmario.setAdapter(adaptador);

                }
            }
        });

    }
}
