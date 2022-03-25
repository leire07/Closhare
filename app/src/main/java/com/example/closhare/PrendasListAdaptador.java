package com.example.closhare;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Color;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrendasListAdaptador extends RecyclerView.Adapter<PrendasListAdaptador.ViewHolder>{

    private Context context;
    private List<HashMap> prendas;
//    private LayoutInflater mInflates;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
//    FirebaseUser usuario;
//    boolean isActivatedButton;


    public class ViewHolder extends RecyclerView.ViewHolder{
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

//        List<Object> prenda = new ArrayList<>();
//        holder.tarea.setText(tareas.get(position).getTarea());

//        for(int i=0; i<prendas.size(); i++){
//            tareaa.add(i, (String) prendas.get(i).get("tarea"));
//            prenda.add(i, )
//        }

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
                    holder.etiqueta_color.setBackgroundColor(0x00FF0000);

//                    holder.etiqueta_color.setBackgroundColor();
                    String a = "sljnf";


                }
            }

        });
//        holder.tarea.setText(tareaa.get(position).toString());



//        db.collection("Tareas")
//                .document(mAuth.getCurrentUser().getUid())
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isComplete()){
//
//                    List<String> tareaa = new ArrayList<>();
////                    List<Long> verde = new ArrayList<>();
////                    List<Long> azul = new ArrayList<>();
//
//                    for(int i=0; i<task.getResult().getData().size(); i++){
////                        Log.d("Color", String.valueOf(task.getResult().getData().get("Prueba1")));
////                        Log.d("Modo2", modo.get(i).toString());
//
//                        tareaa.add(i, (String) tareas.get(i).get("tarea"));
////                        verde.add(i, (Long) modo.get(i).get("green"));
////                        azul.add(i, (Long) modo.get(i).get("blue"));
//                    }
////                    holder.button.setText("Modo" + (modo.indexOf(modo.get(position))+1));
////                    holder.text.setText("Modo" + (modo.indexOf(modo.get(position))+1));
////                    holder.button.setBackgroundColor(getIntFromColor(Math.toIntExact(rojo.get(position)), Math.toIntExact(verde.get(position)), Math.toIntExact(azul.get(position))));
////                    holder.button.setBackgroundResource(R.drawable.bordes_redondos_botton);
////                    holder.button.setColorFilter(getIntFromColor(Math.toIntExact(rojo.get(position)), Math.toIntExact(verde.get(position)), Math.toIntExact(azul.get(position))));
//                    holder.tarea.setText(tareaa.get(position).toString());
////                    Log.d("RedArray", rojo.toString());
//
//                }
//            }
    }

    public String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }

    public void setItems(List<HashMap> items){prendas = items;}
}
