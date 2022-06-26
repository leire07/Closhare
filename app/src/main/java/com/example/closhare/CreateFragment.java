package com.example.closhare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreateFragment extends Fragment {

    Button prenda, aleatorio, coleccion;
    FirebaseStorage storage;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.window_nuevo, container, false);

        //  Definiciones de variables del layout
        prenda = view.findViewById(R.id.prenda);
        aleatorio = view.findViewById(R.id.aleatorio);
        coleccion = view.findViewById(R.id.coleccion);

//      Definicion de las variables de base de datos
        StorageReference storageRef = storage.getReference();


// Nos abre la camara o la galeria para anadir nueva foto al storage.
        prenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "click prenda");
                Toast.makeText(getContext(), "click prenda", Toast.LENGTH_SHORT).show();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                }

            }
        });

        aleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "click aleatorio");
                Toast.makeText(getContext(), "click aleatorio", Toast.LENGTH_SHORT).show();
            }
        });

        coleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "click coleccion");
                Toast.makeText(getContext(), "click coleccion", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}