package com.example.closhare.armario;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.closhare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrendasListAdaptador extends RecyclerView.Adapter<PrendasListAdaptador.ViewHolder>{

//    Difiniendo variables
    private Context context;
    private List<HashMap> prendas;
    FirebaseAuth mAuth;
    FirebaseFirestore db;


    public class ViewHolder extends RecyclerView.ViewHolder{
//        Difinimos los objetos que tenemos en el layout
        ImageView imagePrenda;
        Button etiqueta_color;

        public ViewHolder (@NonNull View itemView){
            super(itemView);
            imagePrenda = itemView.findViewById(R.id.imagePrenda);
            etiqueta_color = itemView.findViewById(R.id.etiqueta_color);
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
        }

        void bindData(final PrendasList item ){
//            Aqui sacamos la foto y color de etiqueta
            Picasso.get()
                    .load(item.getFotoPrenda())
                    .into(imagePrenda);

            etiqueta_color.setBackgroundColor(item.getNombreColor());
        }
    }

    public PrendasListAdaptador(Context context, List<HashMap> prenda) {
        this.context = context;
        this.prendas = prenda;
    }

    @Override
    public int getItemCount() {
        return prendas.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_armario,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder,  int position){
//        Hacemos la consulta a bd para sacar el array con cada prenda
        db.collection("Armario")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isComplete()){

                    List<String> foto = new ArrayList<>();
                    List<String> color = new ArrayList<>();

                    for(int i=0; i<task.getResult().getData().size(); i++){
//                        Log.d("Color", String.valueOf(task.getResult().getData().get("Prueba1")));
//                        Log.d("Prenda", prendas.get(position).toString());

                        foto.add(i, (String) prendas.get(i).get("foto"));
                        color.add(i, (String) prendas.get(i).get("numero"));
                    }


//                    TODO: como cogeremos el color de la imagen? de esto dependo como ;e enviaremos info a bd
//                    Meteremos rgb para el color
//                    holder.etiqueta_color.setBackgroundColor(prendas.indexOf(prendas.get(position)));
//                    holder.etiqueta_color.setBackgroundColor(0xFF000000);

                }
            }

        });
    }

    public String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }

    public void setItems(List<HashMap> items){prendas = items;}
}
