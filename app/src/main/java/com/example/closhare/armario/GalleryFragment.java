package com.example.closhare.armario;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.closhare.R;
import com.example.closhare.armario.PrendasListAdaptador;
import com.example.closhare.home.PruebaHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GalleryFragment extends Fragment {

    RecyclerView recyclerViewArmario;
    PrendasListAdaptador adaptador;
    //    TODO poder fotos en mapa
    List<HashMap> leer = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    TextView prendaNul;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_armario, container, false);
                // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        prendaNul = view.findViewById(R.id.prendaNulo);
        recyclerViewArmario = view.findViewById(R.id.recyclerViewArmario);

        db.collection("Armario")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    if (task.getResult().getData() == null){

                        prendaNul.setVisibility(View.VISIBLE);

                    } else {
                        for(int i=0; i<task.getResult().getData().size(); i++){
                            leer.add(i, (HashMap) task.getResult().getData().get("Foto"+i));
                        }

                        Log.d("Leer", leer.toString());

//        La captura de como rellenarlo completo esta en es escritorio.
                        recyclerViewArmario.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        adaptador = new PrendasListAdaptador(getContext(), leer);
                        recyclerViewArmario.setAdapter(adaptador);

                    }
                }
            }
        });

        return view;
    }
}