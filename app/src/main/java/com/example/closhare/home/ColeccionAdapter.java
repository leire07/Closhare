package com.example.closhare.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.closhare.R;
import com.example.closhare.armario.PrendasList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ColeccionAdapter extends RecyclerView.Adapter<ColeccionAdapter.ViewHolder>{

    private Context context;
    private List<String> fotosConjunto;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageConjunto, heart_full, heart_empty;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            imageConjunto = itemView.findViewById(R.id.imageView13);
            heart_empty = itemView.findViewById(R.id.heart_empty);
            heart_full = itemView.findViewById(R.id.heart_fill);

            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();

            heart_full.setVisibility(View.GONE);

            if(heart_full.getVisibility() == View.GONE){
                heart_empty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        heart_empty.setVisibility(View.GONE);
                        heart_full.setVisibility(View.VISIBLE);
                    }
                });
            } else if(heart_empty.getVisibility() == View.GONE) {
                heart_full.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        heart_full.setVisibility(View.GONE);
                        heart_empty.setVisibility(View.VISIBLE);
                    }
                });
            }


        }

        void bindData(final ColeccionesList item ){

        }
    }

    public ColeccionAdapter(Context context, List<String> fotosConjunto) {
        this.context = context;
        this.fotosConjunto = fotosConjunto;
    }

    @Override
    public int getItemCount() {
        return fotosConjunto.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_coleccion,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder,  int position){

        for(int i=0; i<fotosConjunto.size(); i++){

            Glide.with(context)
                    .load(fotosConjunto.get(position))
                    .into(holder.imageConjunto);
        }

    }


    public void setItems(List<String> items){fotosConjunto = items;}
}
