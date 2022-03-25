package com.example.closhare.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closhare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColeccionesListAdaptador extends RecyclerView.Adapter<ColeccionesListAdaptador.ViewHolder>{

    private Context context;
    private List<HashMap> colecciones;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ColeccionAdapter adapter;


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreColeccion;
        RecyclerView recycler_coleccion;

        public ViewHolder (@NonNull View itemView){
            super(itemView);
            nombreColeccion = itemView.findViewById(R.id.nombre_coleccion);
            recycler_coleccion = itemView.findViewById(R.id.recycler_coleccion);
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
        }

        void bindData(final ColeccionesList item ){

//            Aqui sacamos la foto y color de etiqueta
//            Picasso.get()
//                    .load(item.getFotoPrenda())
//                    .into(imagePrenda);

//            etiqueta_color.setBackgroundColor(item.getNombreColor());
        }
    }

    public ColeccionesListAdaptador(Context context, List<HashMap> colecciones) {
        this.context = context;
        this.colecciones = colecciones;
    }

    @Override
    public int getItemCount() {
        return colecciones.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_colecciones,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder,  int position){

        List<String> fotos = new ArrayList<>();

        Log.d("Colecciones ", String.valueOf(colecciones.get(position).get("Nombre")));
        holder.nombreColeccion.setText((String) colecciones.get(position).get("Nombre"));

        for(int i=0; i<colecciones.size()+1; i++){
            if(colecciones.get(position).get("Foto" + i) != null){
                fotos.add(i, (String) colecciones.get(position).get("Foto"+i));
            }
        }

        Log.d("Foto lista ", fotos.toString());

        holder.recycler_coleccion.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
//        La captura de como rellenarlo completo esta en es escritorio.

        adapter = new ColeccionAdapter(context.getApplicationContext(), fotos);
        holder.recycler_coleccion.setAdapter(adapter);

    }


    public void setItems(List<HashMap> items){
        colecciones = items;}
}
