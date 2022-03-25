package com.example.closhare.no_autorizado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.closhare.PruebaArmario;
import com.example.closhare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {


//    Definiendo objetos de la vista
    Button entrar;
    TextView crear_cuenta, restaurar_pass, prueba;
    TextInputEditText usuario, password;

//    Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser usuarioo;

//    Resto
    private final String url = "https://api.openweathermap.org/data/2.5/weather?q=Gandia&units=metric&appid=e96051f26738be95560f9d1a8d60feb6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Difiniendo objetos con id de los elementos
        entrar = findViewById(R.id.login_entrar);
        crear_cuenta = findViewById(R.id.crear_cuenta);
        restaurar_pass = findViewById(R.id.restaurar_pass);
        usuario = findViewById(R.id.login_username);
        password = findViewById(R.id.login_pass);
        prueba = findViewById(R.id.prueba_api);

//        Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usuarioo = FirebaseAuth.getInstance().getCurrentUser();

//        onClicks
        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registrar.class));
            }
        });

        restaurar_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RestaurarPass.class));
            }
        });

//        Funcion para entrar verificar y logear al usuario
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Declaramos strings como los valores de los inputs
                String user = usuario.getText().toString();
                String pass = password.getText().toString();

//                Compobamos que los campos esten rellenados
                if(!user.isEmpty() && !pass.isEmpty()){
                    mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                if(!mAuth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(getApplicationContext(), "Verifica tu correo electronico", Toast.LENGTH_SHORT).show();
                                } else {
//                                    TODO
//                                   Entramos en HomePage
                                   startActivity(new Intent(getApplicationContext(), PruebaArmario.class));
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Incorrecto usuario y/o contraseña", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
//                Si los campos estan vacios
                if(user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Rellena los campos, por favor", Toast.LENGTH_SHORT).show();
                }
//                Si correo tiene la estructura incorrecta
                if(!isEmailValid(user)){
                    Toast.makeText(getApplicationContext(), "El email tiene la forma incorrecta", Toast.LENGTH_SHORT).show();
                }
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

                    prueba.setText(String.valueOf(tempa) + "°C");
//                    location.setText(city);
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
    }

//    La funcion para comprobar que la frase sea email
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}