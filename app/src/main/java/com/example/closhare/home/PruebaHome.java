package com.example.closhare.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.closhare.R;
import com.example.closhare.armario.PruebaArmario;
import com.example.closhare.no_autorizado.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PruebaHome extends AppCompatActivity {

    Button coleccion, salir;
    private final String url = "https://api.openweathermap.org/data/2.5/weather?q=Gandia&units=metric&appid=e96051f26738be95560f9d1a8d60feb6";
    TextView saludo, tiempoAhora, location, coleccionNull;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    RecyclerView recyclerViewColecciones;
    ColeccionesListAdaptador adaptador;
    //    TODO poder fotos en mapa
    List<HashMap> listColecciones = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_colecciones);


        saludo = findViewById(R.id.home_hello);
        tiempoAhora = findViewById(R.id.tiempoAhora);
        location = findViewById(R.id.location);
        coleccionNull = findViewById(R.id.coleccionNulo);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        salir = findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PruebaHome.this, Login.class));
            }
        });

        coleccion = findViewById(R.id.a_coleccion);
        coleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PruebaArmario.class));
            }
        });



        //        Funcion para APIWeather
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    int tempa = jsonObjectMain.getInt("temp");
                    String city = jsonResponse.getString("name");
                    String icon = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("icon");

                    //String urlfoto  = "http://openweathermap.org/img/wn/" + icon + ".png";
//                    Log.d("Demo" , urlfoto);

                    tiempoAhora.setText(String.valueOf(tempa) + "°C");
                    location.setText(city);
//                    new FetchImage(url).start();
//                    Picasso.get()
//                            .load(urlfoto)
//                            .into(iconWeather);

//                    Glide.with(getContext())
//                            .load(url)
//                            .into(iconWeather);

//                    InputStream inputStream = null;
//                    inputStream = new URL(url).openStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    iconWeather.setImageBitmap(bitmap);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        setup();
    }

    private void setup(){

        db.collection("Users")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    if(task.getResult().getString("Nombre") == null){
                        saludo.setText("Ninguno usuario esta entrado");
                        saludo.setTextSize(20);
                    } else {
                        String name = task.getResult().getString("Nombre");
                        saludo.setText("Hola, " + name);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Se ha producido error al leer base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        db.collection("Colecciones")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    Log.d("task", task.getResult().toString());

                    if(task.getResult().getData() == null){
                        coleccionNull.setVisibility(View.VISIBLE);
                    } else {

                        for(int i=0; i<task.getResult().getData().size(); i++){
                            listColecciones.add(i, (HashMap) task.getResult().getData().get("Coleccion"+i));
                        }

                        Log.d("listColecciones ", listColecciones.toString());

                        recyclerViewColecciones = findViewById(R.id.recycler_colecciones);
//        La captura de como rellenarlo completo esta en es escritorio.
                        recyclerViewColecciones.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adaptador = new ColeccionesListAdaptador(getApplicationContext(), listColecciones);
                        recyclerViewColecciones.setAdapter(adaptador);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Se ha producido error al leer base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
